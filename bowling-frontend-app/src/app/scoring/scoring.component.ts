import { Component, OnInit } from '@angular/core';
import { Game } from './game';
import {ScoringService} from '../service/scoring.service';

@Component({
  selector: 'app-scoring',
  templateUrl: './scoring.component.html',
  styleUrls: ['./scoring.component.css']
})
export class ScoringComponent implements OnInit {
  newPlayerName : String;
  game: Game;
  constructor(private scoringService: ScoringService) { }

  ngOnInit() {
   this.game = new Game();
    this.newPlayerName="";
  }

  addPlayer(player):void{

    this.game.players.push(player);
    this.newPlayerName="";
  }

  removePlayer(player):void{

    this.game.players.splice( this.game.players.indexOf(player), 1);

  }

  startGame(): void
  {
    this.game.gameId=this.scoringService.startGame(this.game);
  }
}
