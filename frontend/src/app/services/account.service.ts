import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from './api.service';
import { user } from '../interface/user';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  authenticated: boolean = false;
  token: string = "";
  username: string = "";
  
  constructor(private apiService: ApiService, private router: Router) { }

  login(username: string, password: string): void {

    this.apiService.postLogin(username, password)
      .then(response => {
        console.log(response);
        const str = response.data.token.slice(0, 5);
        if(str === 'Error') {
          window.alert(response.data.token);
        } else {
          this.token = response.data.token;
          this.username = response.data.username;
          this.authenticated = true;
          this.apiService.updateAuthorizationHeaders(this.token);
          this.router.navigate(["home"]);
          window.alert("Success!");
        }
      }).catch(err => {
        console.log(err);
      })
  }

  logOut(): void {
    console.log('logout');
    this.authenticated = false;
    this.token = "";
    this.apiService.updateAuthorizationHeaders("");
    // this.router.navigate(["login"]);
    // Bandage solution
    // To do: https://www.youtube.com/watch?v=0GGFZdYe-FY
    window.location.href = "/login";
  }

  register(username: string, password: string): void {
    this.apiService.postRegister(username, password)
      .then(response => {
        console.log(response);
        if(response.status === 201) {
          this.router.navigate(["login"]);
          window.alert(response.data);
        }
      })
      .catch(err => {
        console.log(err);
        window.alert(err.response.data);
      });
  }

  getUsersFromChatroom(id: number): Promise<user[]> {
    const members: Promise<user[]> = this.apiService.getUsersFromChatroom(id)
      .then(response => {
        const users: user[] = response.data;
        return users;
      })
      .catch(err => {
        console.log(err);
        return [];
      });
    return members;
  }
}
