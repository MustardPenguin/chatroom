import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { message } from '../interface/message';
import { StompService } from './stomp.service';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private apiService: ApiService, private stompService: StompService) { }

  getMessageFromChatroom(id: number, page: number): Promise<message[]> {
    const messages = this.apiService.getMessagesFromChatroom(id, page)
      .then(response => {
        console.log(response);
        const messagesResponse: message[] = response.data;
        return messagesResponse;
      })
      .catch(err => {
        console.log(err);
        return [];
      });
      return messages;
  }

  postMessage(id: number, message: string) {
    this.stompService.publishMessage(id, message);
  }
}
