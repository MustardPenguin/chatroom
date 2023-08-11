import { Injectable, OnInit } from '@angular/core';
import { chatroom } from '../interface/chatroom';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class ChatroomService {

  chatrooms: chatroom[] = [];

  constructor(private apiService: ApiService) {}

  async getChatrooms(page: number) {
    const getRequest = (): Promise<chatroom[]> => this.apiService.getChatrooms(page)
      .then(response => {
        console.log(response);
        const chatrooms: chatroom[] = response.data;
        return chatrooms;
      })
      .catch(err => {
        console.log(err);
        return [];
      });
    
    const response = await getRequest();
    return response;
  }

  joinRoom(id: number) {
    this.apiService.joinChatroom(id)
      .then(response => {
        console.log(response);
      }).catch(err => {
        console.log(err);
      })
  }
}
