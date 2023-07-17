import { Component, OnInit } from '@angular/core';
import { AccountService } from '../services/account/account.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  authenticated: boolean = false;

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
      this.authenticated = this.accountService.authenticated;
  }
}
