import { Injectable } from '@angular/core';
import { Client, Stomp } from '@stomp/stompjs';

const stompClient = new Client({
  brokerURL: "ws://localhost:8080/chat",
  debug: (str: string) => { console.log(str); }
});

interface messageResponse {
  body: string, headers: {}, statusCode: string, statusCodeValue: number
}

stompClient.onConnect = (frame) => {
  console.log('Connected ' + frame);
  stompClient.subscribe('/topic/message', (message) => {
    const messageResponse = JSON.parse(message.body);
    if(messageResponse.statusCode === "OK") {
      console.log(messageResponse.body);
    } else {
      console.log("Error receiving message response from subscription");
    }
  });
}

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error);
  stompClient.deactivate();
};

stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
};

@Injectable({
  providedIn: 'root'
})
export class StompService {
  activated: boolean = false;

  constructor() { }

  activateStompClient(): void {
    if(stompClient.connected) {
      this.deactivateStompClient();
    }
    stompClient.activate();
  }

  deactivateStompClient(): void {
    stompClient.deactivate();
  }

  publishMessage(message: string): void {
    stompClient.publish({
      destination: "/chat",
      body: JSON.stringify({
        message: message
      })
    });
    console.log("publish message");
  }
}
