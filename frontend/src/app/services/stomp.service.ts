import { Injectable, OnDestroy, OnInit } from '@angular/core';
import { Client, CompatClient, Stomp } from '@stomp/stompjs';
import { AccountService } from './account.service';
import * as SockJS from 'sockjs-client';
import { message } from '../interface/message';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';

interface messageResponse {
  body: message, headers: {}, statusCode: string, statusCodeValue: number
}

@Injectable({
  providedIn: 'root'
})
export class StompService {
  private activated: boolean = false;

  private stompClient: CompatClient = Stomp.over(new SockJS('http://localhost:8080/chat'));

  constructor(private accountService: AccountService, private router: Router) { 
    const url = this.router.url;
    console.log('new stomp client');
    // router.events.forEach((event) => {
    //   if(event instanceof NavigationStart) {
    //     // this.router.url != url && 
    //     if(this.stompClient.connected) {
    //       this.deactivateStompClient();
    //     }
    //   }
    // });
  }

  activateStompClient(callback: (message: message, messages: message[]) => void, messages: message[]): void {
    this.stompClient = Stomp.over(new SockJS('http://localhost:8080/chat'));
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
    console.log("Deactivating stomp client");
    this.stompClient.deactivate();
  }
}
