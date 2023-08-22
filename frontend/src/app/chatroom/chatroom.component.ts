import { Component, OnInit } from '@angular/core';
import { AccountService } from '../services/account.service';
import { ChatroomService } from '../services/chatroom.service';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css']
})
export class ChatroomComponent implements OnInit {

  constructor(
    private accountService: AccountService, 
    private chatroomService: ChatroomService,
    private apiService: ApiService
    ) {}

  ngOnInit(): void {
    
  }
}
