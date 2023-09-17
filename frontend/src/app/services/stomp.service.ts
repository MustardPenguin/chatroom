import { Injectable, OnInit } from '@angular/core';
import { Client, CompatClient, Stomp } from '@stomp/stompjs';
import { AccountService } from './account.service';
import * as SockJS from 'sockjs-client';


// import sockjs from "sockjs-client/dist/sockjs";

interface messageResponse {
  body: any, headers: {}, statusCode: string, statusCodeValue: number
}

@Injectable({
  providedIn: 'root'
})
export class StompService {
  activated: boolean = false;

  private stompClient: CompatClient = Stomp.over(new SockJS('http://localhost:8080/chat'));
  // private stompClient: CompatClient = Stomp.over(function() {
  //   return new WebSocket('ws://localhost:8080/chat');
  // });


  constructor(private accountService: AccountService) { }

  /////////////////////
  activateStompClient(): void {
    // const socket = new SockJS('ws://localhost:8080/chat');
    // this.stompClient = Stomp.over(socket);
    
    const headers = {
      // login: 'thedog', passcode: 'thecat',
      Authorization: 'Bearer ' + this.accountService.token
    };
    // console.log(this.stompClient);
    console.log(this.stompClient);
    this.stompClient.connect(headers, (frame: any) => {
      console.log('connected');
      
      console.log(frame);
      // this.stompClient.subscribe('/topic/message', (message) => {
      //   const messageResponse: messageResponse = JSON.parse(message.body);
      //   console.log(message);
      //   if(messageResponse.statusCode === "OK") {
      //     console.log(messageResponse);
      //   } else {
      //     console.log("Error receiving message response from subscription");
      //   }
      // });
    }, (error: any) => console.log(error));
    
    // this.stompClient.onStompError = function(frame) {
    //   console.log('Error');
    //   console.log(frame);
    //   console.log('-----------');
    // }
  }

  publishMessage(id: number, message: string): void {
    this.stompClient.send(`/chatroom/${id}/message`, {
      Authorization: 'Bearer ' + this.accountService.token
    }, JSON.stringify({
      message: message,
      token: 'Bearer ' + this.accountService.token
    }));
  }

  deactivateStompClient(): void {

  }
  ///////////////////


  
  // publishMessage(id: number, message: string): void {
  //   this.stompClient.publish({
  //         destination: `/chatroom/${id}/message`,
  //         // destination: `/chatroom/${id}/message`,
  //         body: JSON.stringify({
  //           message: message
  //         })
  //     });
  //   console.log("publish message");
  // }
}
