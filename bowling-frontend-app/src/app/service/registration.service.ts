import { Injectable } from '@angular/core';
import { UserAccount} from '../registration/user-account';
import { HttpClient} from '@angular/common/http';

@Injectable()
export class RegistrationService {

  constructor(private http: HttpClient) { }

  register(account: UserAccount) : boolean {



   // account.birthDate = account.birthDate.getTime();

    this.http.post('/api/account/register', account).subscribe(data => {
        console.log('working fine');
      },
      // Errors will call this callback instead:
      err => {
        console.log('Something went wrong!');
      }
    );
    return  true;

  }

}
