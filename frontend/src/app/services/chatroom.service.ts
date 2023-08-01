import { Injectable, OnInit } from '@angular/core';
import { chatroom } from '../interface/chatroom';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class ChatroomService {

  chatrooms: chatroom[] = [

  ];

  constructor(private apiService: ApiService) {}

  async getChatrooms() {
    this.chatrooms = [];
    const chatrooms = await this.getRequestChatroom();
    this.chatrooms = chatrooms;
    
    return this.chatrooms;
  }

  async getRequestChatroom(): Promise<chatroom[]> {
    const getRequest = (): Promise<chatroom[]> => this.apiService.getChatrooms()
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
    console.log(response);
    return response;
  }
}
