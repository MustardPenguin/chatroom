import { Component, ElementRef, OnDestroy, OnInit, Renderer2 } from '@angular/core';
import { AccountService } from '../services/account.service';
import { ChatroomService } from '../services/chatroom.service';
import { ApiService } from '../services/api.service';
import { chatroom } from '../interface/chatroom';
import { ActivatedRoute } from '@angular/router';
import { message } from '../interface/message';
import { MessageService } from '../services/message.service';
import { StompService } from '../services/stomp.service';
import { user } from '../interface/user';

@Component({
  selector: 'app-chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css']
})
export class ChatroomComponent implements OnInit, OnDestroy {
  chatroom: chatroom = {
    id: 1, name: "Loading...", dateCreated: "Loading...", members: 1, joined: true
  };
  username: string = "thecat";

  messages: message[] = [];

  members: user[] = [];

  constructor(
    private accountService: AccountService, 
    private chatroomService: ChatroomService,
    private messageService: MessageService,
    private stompService: StompService,
    private apiService: ApiService,
    private route: ActivatedRoute,
    private renderer: Renderer2,
    private elementRef: ElementRef,
    ) {}

    ngOnDestroy(): void {
        console.log('destroyed chatroom component');
        this.stompService.deactivateStompClient();
    }

  async ngOnInit(): Promise<void> {
    const id: number = +(this.route.snapshot.paramMap.get('id') || -1);

    this.stompService.activateStompClient(this.onMessageReceived, this.messages);

    this.chatroom = await this.chatroomService.getChatroom(id) || this.chatroom;

    const messages = await this.messageService.getMessageFromChatroom(id);

    for(let i = 0; i < messages.length; i++) {
      this.messages.push(messages[i]);
    }
    this.formatMessages();

    const users = await this.accountService.getUsersFromChatroom(this.chatroom.id);
    this.members = users;

    console.log(this.chatroom);
  }

  formatMessages() {
    let previousUsername = "";

    // for(let message of this.messages) {
    //   if(message.username === previousUsername) {
    //     message.username = "";
    //   } else {
    //     previousUsername = message.username;
    //   }
    // }
  }

  onMessageReceived(message: message, messages: message[]): void {
    console.log(message);
    messages.unshift(message);
  }

  filterDate(date: string): string {
    let formattedDate: string = date.slice(0, -7);
    const i = formattedDate.indexOf('T');
    formattedDate = date.slice(0, i) + " at " + date.slice(i + 1, -7);
    return formattedDate;
  }

  sendMessage(): void {
    const content = document.querySelector(".contenteditable");
    const text = content?.innerHTML;
    if(text === '') { return; }
    console.log(text);
    this.messageService.postMessage(this.chatroom.id, text as string);
  }

  handleInputs(keyboardEvent: KeyboardEvent, event: Event): void {
    if(keyboardEvent.key === 'Enter' && !keyboardEvent.shiftKey) {
      this.sendMessage();
      const target = event.target as HTMLElement;
      target.innerHTML = "";
      keyboardEvent.preventDefault();
    }
  }
}
