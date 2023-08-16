import { AfterViewInit, Component, Directive, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { AccountService } from '../services/account.service';
import { ChatroomService } from '../services/chatroom.service';
import { chatroom } from '../interface/chatroom';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {
  authenticated: boolean = false;
  currentList: string = "created";
  username: string = "";

  constructor(
    private accountService: AccountService, 
    private chatroomService: ChatroomService,
    private renderer: Renderer2,
    private elementRef: ElementRef) {}

  ngOnInit(): void {
      this.authenticated = this.accountService.authenticated;
      this.username = this.accountService.username;
      // this.chatroomService.getChatrooms();
  }

  ngAfterViewInit(): void {
      const buttons: HTMLElement[] = this.elementRef.nativeElement.querySelectorAll('.home-btn');
      
      buttons.forEach(element => {
        element.addEventListener('click', () => {
          const attribute = element.getAttribute("value");
          
          this.currentList = attribute || this.currentList;
          console.log(this.currentList);
        });
      });
      
  }

  getRooms(): chatroom[] {
    return this.chatroomService.chatrooms;
  }
}
