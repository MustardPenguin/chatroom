import { Component } from '@angular/core';
import { AccountService } from '../services/account.service';
import { ChatroomService } from '../services/chatroom.service';
import { chatroom } from '../interface/chatroom';

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.css']
})
export class RoomsComponent {
  authenticated: boolean = false;

  constructor(private accountService: AccountService, private chatroomService: ChatroomService) {}

  ngOnInit(): void {
      this.authenticated = this.accountService.authenticated;
      this.chatroomService.getChatrooms();
  }

  getRooms(): chatroom[] {
    return this.chatroomService.chatrooms;
  }

  joinRoom(e: Event): void {
    e.preventDefault();
    const id = +((e.target as Element).getAttribute("value") || -1);
    console.log(id);
    
    this.chatroomService.joinRoom(id);
  }
}
