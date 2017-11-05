import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Game } from '../scoring/game';
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/map";
import {Throw} from "../scoring/throw";

@Injectable()
export class ScoringService {

  constructor(private http: HttpClient) { }
  gameId: string;

  startGame(game: Game) :  Observable<Game> {
   return this.http.post<Game>('/api/game/start', game);
  }

  playGame(pins: Throw) :  Observable<Game> {
    return this.http.post<Game>('/api/game/score', pins);
  }
}
