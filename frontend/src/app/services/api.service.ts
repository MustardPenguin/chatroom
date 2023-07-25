import { Injectable } from '@angular/core';
import axios, { AxiosResponse, AxiosStatic } from 'axios';

const api = axios.create({
  baseURL: "http://localhost:8080"
}) as AxiosStatic;

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor() { }

  updateAuthorizationHeaders(authorizationToken: string): void {
    api.interceptors.request.use(config => {
      config.headers.Authorization = authorizationToken;
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

  getTest() {
    return api.get('/test', {
      headers: {
        Authorization: 'Basic ' + btoa("user:dummy")
      }
    })
  };

  
}
