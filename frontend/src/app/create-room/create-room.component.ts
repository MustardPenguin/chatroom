import { Component } from '@angular/core';
import { ApiService } from '../services/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-room',
  templateUrl: './create-room.component.html',
  styleUrls: ['./create-room.component.css']
})
export class CreateRoomComponent {

  room: { name: string } = {
    name: ""
  };

  constructor(private apiService: ApiService, private router: Router) {}

  createRoom(event: Event): void {
    event.preventDefault();
    console.log(this.room.name);
    if(this.room.name.length === 0 || this.room.name.length < 3) {
      return;
    }

    this.apiService.postCreateRoom(this.room.name)
      .then(response => {
        console.log(response);
        if(response.status === 201) {
          window.alert("Successfully created room");
          this.router.navigate(["/home"]);
        }
      })
      .catch(err => {
        console.log(err);
        window.alert(err.response.data);
      });
  }

  getTest(): void {
    this.apiService.getTest()
      .then(response => {
        console.log(response);
      })
      .catch(err => {
        console.log(err);
      })
  }
}
