import { Component, OnInit } from '@angular/core';
import { HttpClient} from '@angular/common/http';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  firstName : string;
  lastName : string;
  birthDate : Date;
  email: string;
  phoneNumber: string;
  userId : string;
  password: string;

  // Inject HttpClient into your component or service.
  constructor(private http: HttpClient) { }

  ngOnInit(): void {

  }

}



