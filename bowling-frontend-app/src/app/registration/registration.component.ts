import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../service/registration.service';
import { UserAccount} from './user-account';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  userAccount: UserAccount;

  // Inject HttpClient into your component or service.
  constructor(private registrationService: RegistrationService) { }

  ngOnInit(): void {
    this.userAccount = new UserAccount();
    // Make the HTTP request:
   /* this.http.get('/assets/data/items.json').subscribe(data => {
        // Read the result field from the JSON response.
        this.name = data['name'];
        this.email = data['email'];
        this.phoneNumber = data['phoneNumber'];
      },
      // Errors will call this callback instead:
      err => {
        console.log('Something went wrong!');
      }
    );  */


  }

  submit(): void{
    this.registrationService.register(this.userAccount)
  }

  clear(): void{
    this.userAccount = new UserAccount();
  }
}



