import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
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
export class ChatroomComponent implements OnInit {
  chatroom: chatroom = {
    id: 1, name: "Loading...", dateCreated: "Loading...", members: 1, joined: true
  };
  username: string = "thecat";
  // Testing
  messages: message[] = [];
  // messages: message[] = [
  //   { username: "thedog", date: "07-07-7777 at 7:77", message: "first" },
  //   { username: "thedog", date: "07-07-7777 at 7:77", message: "second" },
  //   { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" },
  //   { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" },
  //   { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" },
  //   { username: "thecat", date: "07/07/7777 at 7:77", message: "cats" },
  //   { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" }, { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" }, { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" }, { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" }, { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" }, { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" }, { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" }, { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" }, { username: "thedog", date: "07-07-7777 at 7:77", message: "cats" },
  //   { username: "thecat", date: "07/07/7777 at 7:77", message: "cats" },
  // ]

    members: user[] = [];
  // members: string[] = [
  //   "first", "testing", "testingggggggggggg", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", 
  //   "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", 
  //   "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", 
  //   "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", 
  // ];

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

  async ngOnInit(): Promise<void> {
    const id: number = +(this.route.snapshot.paramMap.get('id') || -1);
    console.log(id);
    this.chatroom = await this.chatroomService.getChatroom(id) || this.chatroom;

    console.log(this.chatroom);
    
    const messages = await this.messageService.getMessageFromChatroom(id);
    this.messages = messages;
    this.formatMessages();

    
    const users = await this.accountService.getUsersFromChatroom(this.chatroom.id);
    this.members = users;

    this.stompService.activateStompClient();
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

  sendMessage(): void {
    const content = document.querySelector(".contenteditable");
    const text = content?.innerHTML;
    if(text === '') { return; }
    console.log(text);
    this.messageService.postMessage(this.chatroom.id, text as string);
  }

  handleInputs(keyboardEvent: KeyboardEvent): void {
    if(keyboardEvent.key === 'Enter' && !keyboardEvent.shiftKey) {
      this.sendMessage();
      keyboardEvent.preventDefault();
    }
  }
}
