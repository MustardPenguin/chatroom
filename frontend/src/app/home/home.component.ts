import { Component, OnInit } from '@angular/core';
import { AccountService } from '../services/account/account.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  authenticated: boolean = false;

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
      this.authenticated = this.accountService.authenticated;
  }
}
