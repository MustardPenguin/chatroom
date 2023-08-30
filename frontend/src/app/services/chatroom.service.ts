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

  async getOwnedChatrooms(page: number, username: string) {
    const getRequest = (): Promise<chatroom[]> => this.apiService.getOwnedChatrooms(page, username)
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

  async getJoinedChatrooms(page: number, username: string) {
    const getRequest = (): Promise<chatroom[]> => this.apiService.getJoinedChatrooms(page, username)
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

  getChatroom(id: number) {
    const getRequest = (): Promise<chatroom | null> => this.apiService.getChatroom(id)
      .then(response => {
        const chatroom: chatroom = response.data;
        return chatroom;
      })
      .catch(err => {
        console.log(err);
        return null;
      });

    const response = getRequest();
    return response;
  }

  async joinRoom(id: number): Promise<boolean> {
    return await this.apiService.joinChatroom(id)
      .then(response => {
        console.log(response);
        return response.status === 200;
      }).catch(err => {
        console.log(err);
        return false;
      });
  }

  deleteChatroom(id: number) {
    const deleteRequest = (): Promise<boolean> => this.apiService.deleteChatroom(id)
      .then(response => {
        console.log(response);
        return true;
      })
      .catch(err => {
        console.log(err);
        return false;
      });

      const response = deleteRequest();
      return response;
  }
}
