import { Component, OnInit } from '@angular/core';
import { AccountService } from '../services/account.service';
import { ChatroomService } from '../services/chatroom.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  authenticated: boolean = false;

  constructor(private accountService: AccountService, private chatroomService: ChatroomService) {}

  ngOnInit(): void {
      this.authenticated = this.accountService.authenticated;
      this.chatroomService.getRequestChatroom();
  }
}
