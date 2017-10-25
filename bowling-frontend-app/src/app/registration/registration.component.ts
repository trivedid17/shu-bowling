import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  name : string;
  email: string;
  phoneNumber: string;
  password: string;

  constructor() { }

  ngOnInit() {
    this.name = "hello ";
  }

}
