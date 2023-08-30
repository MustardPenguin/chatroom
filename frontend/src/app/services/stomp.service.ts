import { Injectable } from '@angular/core';
import { Client, Stomp } from '@stomp/stompjs';

const stompClient = new Client({
  brokerURL: "ws:http://localhost:8080/websocket",
  debug: (str: string) => { console.log(str); }
});

stompClient.onConnect = () => {
  console.log('connect');
}

@Injectable({
  providedIn: 'root'
})
export class StompService {

  constructor() { }

  activateStompClient(): void {
    stompClient.activate();
  }
}
