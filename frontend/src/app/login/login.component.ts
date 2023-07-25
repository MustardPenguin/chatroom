import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../services/account/account.service';
import { Form, FormControl } from '@angular/forms';
import { Account } from '../interface/account';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  url: string = "login";
  account: Account = {
    username: "", password: ""
  }

  constructor(
    private accountService: AccountService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.url = this.router.url.slice(1, 2).toUpperCase() + this.router.url.slice(2);
  }

  login(event: Event): void {
    event.preventDefault();
    this.accountService.login(this.account.username, this.account.password);
  }

  register(event: Event): void {
    event.preventDefault();
    if(this.account.username.length < 3 || this.account.password.length < 3) {
      window.alert('Please enter at least 3 characters.');
      return;
    }
    if(!this.checkValid(this.account.username) || !this.checkValid(this.account.password)) {
      window.alert('Please enter at a valid character.');
      return;
    }

    this.accountService.register(this.account.username, this.account.password);
  }

  checkValid(string: string): boolean {
    const regex = /^[a-zA-Z0-9\-]+$/;
    const valid = regex.exec(string);
    return !!valid;
  }
}
