import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  authenticated: boolean = false;
  token?: string;
  
  constructor() { }

  login(): void {
    console.log('login');
  }

  logOut(): void {
    console.log('logout');
  }

  register(): void {
    console.log('register');
    
  }
}
