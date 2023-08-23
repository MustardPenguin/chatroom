import { Component, OnInit } from '@angular/core';
import { AccountService } from '../services/account.service';
import { ChatroomService } from '../services/chatroom.service';
import { ApiService } from '../services/api.service';
import { chatroom } from '../interface/chatroom';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css']
})
export class ChatroomComponent implements OnInit {
  chatroom: chatroom | null = null;

  constructor(
    private accountService: AccountService, 
    private chatroomService: ChatroomService,
    private apiService: ApiService,
    private route: ActivatedRoute
    ) {}

  async ngOnInit(): Promise<void> {
    const id: number = +(this.route.snapshot.paramMap.get('id') || -1);
    console.log(id);
    this.chatroom = await this.chatroomService.getChatroom(id);
    if(this.chatroom) {
      console.log(this.chatroom);
    }
  }
}
