import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  authenticated: boolean = false;
  token?: string;
  
  constructor(private apiService: ApiService, private router: Router) { }

  login(): void {
    console.log('login');
  }

  logOut(): void {
    console.log('logout');
  }

  register(username: string, password: string): void {
    console.log('register');
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
}
