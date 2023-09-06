import { Injectable, OnInit } from '@angular/core';
import { Client, Stomp } from '@stomp/stompjs';

interface messageResponse {
  body: any, headers: {}, statusCode: string, statusCodeValue: number
}

@Injectable({
  providedIn: 'root'
})
export class StompService {
  activated: boolean = false;
  stompClient = new Client({
    brokerURL: "ws://localhost:8080/chat",
    debug: (str: string) => { console.log(str); }
  });

  constructor() { }

  addEvents(): void {
    console.log('init stomp')
    this.stompClient.onConnect = (frame) => {
      console.log('Connected ' + frame);
      console.log("Connecting to websocket");
      this.stompClient.subscribe('/topic/message', (message) => {
        const messageResponse: messageResponse = JSON.parse(message.body);
        console.log(message);
        if(messageResponse.statusCode === "OK") {
          console.log(messageResponse);
        } else {
          console.log("Error receiving message response from subscription");
        }
      });
    }

    this.stompClient.onWebSocketError = (error) => {
      console.error('Error with websocket', error);
      this.stompClient.deactivate();
    };
    
    this.stompClient.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };
  }

  activateStompClient(): void {
    if(this.stompClient.connected) {
      this.deactivateStompClient();
    }
    this.addEvents();
    this.stompClient.activate();
  }

  deactivateStompClient(): void {
    this.stompClient.deactivate();
  }

  publishMessage(id: number, message: string): void {
    this.stompClient.publish({
      destination: `/chatroom/${id}/message`,
      body: JSON.stringify({
        message: message
      })
    });
    console.log("publish message");
  }
}
