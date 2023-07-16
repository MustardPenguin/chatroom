import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../services/account/account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  url: string = "login";

  constructor(
    private accountService: AccountService,
    private router: Router
    ) {}

  ngOnInit(): void {
    this.url = this.router.url.slice(1, 2).toUpperCase() + this.router.url.slice(2);
  }

  login(event: Event): void {
    event.preventDefault();
    this.accountService.login();
  }

  register(event: Event): void {
    event.preventDefault();
    this.accountService.register();
  }
}
