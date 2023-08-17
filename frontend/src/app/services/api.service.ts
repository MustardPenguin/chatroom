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
    return api.get(`/users/${username}/chatrooms`, {});
  }

  joinChatroom(id: number) {
    return api.post(`/chatroom/${id}`, {}, {})
  }
}
