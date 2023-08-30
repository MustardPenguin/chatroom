import { Component } from '@angular/core';
import { AccountService } from '../services/account.service';
import { ChatroomService } from '../services/chatroom.service';
import { chatroom } from '../interface/chatroom';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.css']
})
export class RoomsComponent {
  constructor(
    private accountService: AccountService, 
    private chatroomService: ChatroomService, 
    private router: Router) {}

  ngOnInit(): void {
      // this.chatroomService.getChatrooms();
  }

  getRooms(): chatroom[] {
    return this.chatroomService.chatrooms;
  }

  isAuthenticated(): boolean {
    return this.accountService.authenticated;
  }
}
