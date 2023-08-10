import { Component, Directive, ElementRef, OnInit } from '@angular/core';
import { AccountService } from '../services/account.service';
import { ChatroomService } from '../services/chatroom.service';
import { chatroom } from '../interface/chatroom';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  authenticated: boolean = false;

  constructor(
    private accountService: AccountService, 
    private chatroomService: ChatroomService,
    private elementRef: ElementRef) {}

  ngOnInit(): void {
      this.authenticated = this.accountService.authenticated;
      this.chatroomService.getChatrooms();

      const buttons = this.elementRef.nativeElement.querySelectorAll('.home-btn');
      
      console.log(buttons);
  }

  getRooms(): chatroom[] {
    return this.chatroomService.chatrooms;
  }
}
