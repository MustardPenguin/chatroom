import { Injectable, OnInit } from '@angular/core';
import axios from 'axios';
import { ApiService } from '../api.service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  authenticated: boolean = false;
  token?: string;
  
  constructor(private apiService: ApiService) { }

  login(): void {
    console.log('login');
  }

  logOut(): void {
    console.log('logout');
  }

  register(): void {
    console.log('register');
    this.apiService.postRegister()
      .then(response => {
        console.log(response);
      })
      .catch(err => {

      });
  }
}
