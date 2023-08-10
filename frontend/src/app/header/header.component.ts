import { Component, OnInit } from '@angular/core';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private accountService: AccountService) {}

  isAuthenticated(): boolean {
    return this.accountService.authenticated;
  }

  logout(): void {
    this.accountService.logOut();
  }

}
