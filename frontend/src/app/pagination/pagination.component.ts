import { AfterViewInit, Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { ChatroomService } from '../services/chatroom.service';
import { ApiService } from '../services/api.service';
import { chatroom } from '../interface/chatroom';
import { NumberValueAccessor } from '@angular/forms';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit, AfterViewInit {
  chatrooms: chatroom[] = [];
  page: number = 0;

  constructor(
    private chatroomService: ChatroomService,
    private apiService: ApiService,
    private renderer: Renderer2,
    private elementRef: ElementRef
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

  getRooms(page: number): void {
    const chatrooms = this.chatroomService.getChatrooms(page);
    chatrooms.then(response => {
      if(response.length > 0) {
        this.setRooms(response);
        this.page = page;
      }
    }).catch(err => {
      console.log(err);
      this.chatrooms = [];
    })
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
