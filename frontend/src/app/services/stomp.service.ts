import { Injectable, OnInit } from '@angular/core';
import { Client, CompatClient, Stomp } from '@stomp/stompjs';
import { AccountService } from './account.service';
import * as SockJS from 'sockjs-client';
import { message } from '../interface/message';

interface messageResponse {
  body: message, headers: {}, statusCode: string, statusCodeValue: number
}

@Injectable({
  providedIn: 'root'
})
export class StompService {
  activated: boolean = false;

  private stompClient: CompatClient = Stomp.over(new SockJS('http://localhost:8080/chat'));

  constructor(private accountService: AccountService) { }

  activateStompClient(callback: (message: message, messages: message[]) => void, messages: message[]): void {
    const headers = {
      Authorization: 'Bearer ' + this.accountService.token
    };
    console.log(this.stompClient);
    this.stompClient.connect(headers, (frame: any) => {
      console.log('connected');
      
      console.log(frame);
      this.stompClient.subscribe('/topic/message', (message) => {
        const messageResponse: messageResponse = JSON.parse(message.body);
        if(messageResponse.statusCode === "OK") {
          const messageObject: message = messageResponse.body;
          // console.log(messageObject);

          callback(messageObject, messages);
        } else {
          console.log("Error receiving message response from subscription");
        }
      });
    }, (error: any) => console.log(error));
  }

  publishMessage(id: number, message: string): void {
    this.stompClient.send(`/chatroom/${id}/message`, {
      Authorization: 'Bearer ' + this.accountService.token
    }, JSON.stringify({
      message: message,
    }));
  }

  deactivateStompClient(): void {

  }
}
