import { Injectable } from '@angular/core';
import { Client, Stomp } from '@stomp/stompjs';

const stompClient = new Client({
  brokerURL: "ws://localhost:8080/chat",
  debug: (str: string) => { console.log(str); }
});

stompClient.onConnect = (frame) => {
  console.log('Connected ' + frame);
  // stompClient.subscribe('/topic/message', (message) => {
  //   console.log(message);
  // });
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

  constructor() { }

  activateStompClient(): void {
    stompClient.activate();
  }
}
