import { AfterViewInit, Component, ElementRef, Input, OnInit, Renderer2 } from '@angular/core';
import { ChatroomService } from '../services/chatroom.service';
import { ApiService } from '../services/api.service';
import { chatroom } from '../interface/chatroom';
import { NumberValueAccessor } from '@angular/forms';
import { AxiosResponse } from 'axios';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit, AfterViewInit {
  chatrooms: chatroom[] = [];
  page: number = 0;
  @Input() pageType: string = "rooms";

  constructor(
    private chatroomService: ChatroomService,
    private apiService: ApiService,
    private renderer: Renderer2,
    private elementRef: ElementRef,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.getRooms(0);
  }

  ngAfterViewInit(): void {
      const arrows: HTMLElement[] = this.elementRef.nativeElement.querySelectorAll('.arrow-page');
      arrows.forEach(arrow => {
        arrow.addEventListener('click', () => {
          const attribute = arrow.getAttribute("value");
          if(this.page <= 0 && attribute === 'decrement') {
            return;
          }
          const page = attribute === "increment" ? this.page + 1 : this.page - 1;
          this.getRooms(page);
          console.log(attribute);
        })
      });
  }

  getRooms(page: number, apiFunction?: Function): void {
    // const chatrooms = this.chatroomService.getChatrooms(page);
    const chatrooms = this.getApiFunction(page);
    
    chatrooms.then(response => {
      console.log(response);
      if(response.length > 0) {
        this.setRooms(response);
        this.page = page;
      }
    }).catch(err => {
      console.log(err);
      this.chatrooms = [];
    })
  }

  getApiFunction(page: number) {
    switch(this.pageType) {
      case "rooms":
        return this.chatroomService.getChatrooms(page);
      case "created":
        return this.chatroomService.getOwnedChatrooms(page, this.accountService.username);
      case "joined":
        return this.chatroomService.getChatrooms(page);
    }
    return this.chatroomService.getChatrooms(page);
  }

  setRooms(chatrooms: chatroom[]): void {
    this.chatrooms = chatrooms;
    this.sortById();
  }

  sortById(): void {
    this.chatrooms.sort((roomA, roomB) => {
      return roomA.id < roomB.id ? 1 : -1;
    })
  }
}
