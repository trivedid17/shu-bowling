import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Game } from '../scoring/game';

@Injectable()
export class ScoringService {

  constructor(private http: HttpClient) { }
  gameId: string;

  startGame(game: Game) : string {



    // account.birthDate = account.birthDate.getTime();

    this.http.post('/api/game/start', game).subscribe(data => {
       this.gameId=data.toString();
      },
      // Errors will call this callback instead:
      err => {
        console.log('Something went wrong!');
      }
    );
    return  this.gameId;

  }


}
