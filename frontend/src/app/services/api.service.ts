import { Injectable } from '@angular/core';
import axios, { AxiosResponse, AxiosStatic } from 'axios';
import { AccountService } from './account.service';

const api = axios.create({
  baseURL: "http://localhost:8080"
}) as AxiosStatic;

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor() { }

  updateAuthorizationHeaders(authorizationToken: string): void {
    console.log("Bearer " + authorizationToken);
    api.interceptors.request.use(config => {
      config.headers.Authorization = "Bearer " + authorizationToken;
      return config;
    });
  };

  postRegister(username: string, password: string) {
    return api.post('/register', { // change to register later
      username: username, password: password
    });
  };

  postLogin(username: string, password: string) {
    return api.post('/login', {
        username: username, password: password
    });
  }

  postCreateRoom(name: string) {
    return api.post("/chatroom", {
      name: name
    });
  }

  getTest() {
    return api.get('/test', {})
  };

  getChatroom(id: number) {
    return api.get(`/chatroom/${id}`);
  }

  getChatrooms(page: number) {
    const URIQuery = `?page=${page}`;
    return api.get(`/chatroom${URIQuery}`, {});
  }

  getOwnedChatrooms(page: number, username: string) {
    const URIQuery = `?page=${page}`;
    return api.get(`/users/${username}/CreatedChatrooms${URIQuery}`, {});
  }

  getJoinedChatrooms(page: number, username: string) {
    const URIQuery = `?page=${page}`;
    console.log(username);
    return api.get(`/users/${username}/chatrooms${URIQuery}`, {});
  }

  joinChatroom(id: number) {
    return api.post(`/chatroom/${id}`, {}, {})
  }

  deleteChatroom(id: number) {
    return api.delete(`/chatroom/${id}`);
  }

  getMessagesFromChatroom(id: number, page: number) {
    return api.get(`/chatroom/${id}/message?page=${page}`);
  }

  postMessage(id: number, message: string) {
    return api.post(`/chatroom/${id}/message`, {
      message: message
    });
  }

  getUsersFromChatroom(id: number) {
    return api.get(`/chatroom/${id}/users`);
  }
}
