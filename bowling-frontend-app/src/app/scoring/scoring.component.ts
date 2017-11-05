import {Component, OnInit} from '@angular/core';
import {Game} from './game';
import {ScoringService} from '../service/scoring.service';
import {Player} from "./player";
import {forEach} from "@angular/router/src/utils/collection";
import {Frame} from "./frame";
import {Throw} from "./throw";

@Component({
  selector: 'app-scoring',
  templateUrl: './scoring.component.html',
  styleUrls: ['./scoring.component.css']
})
export class ScoringComponent implements OnInit {
  newPlayerName: String;
  game: Game;


  constructor(private scoringService: ScoringService) {
  }

  ngOnInit() {
    this.game = new Game();
    this.game.laneId = 1;
    this.game.gameId = "";
    this.newPlayerName = "";
  }

  addPlayer(playerName): void {

    let player = new Player();
   for(let i=0; i<10;i++)
   {
     let frame = new Frame();
     player.frames.push(frame);
   }
    player.name = playerName;
    this.game.players.push(player);
    this.newPlayerName = "";
  }

  removePlayer(player): void {

    this.game.players.splice(this.game.players.indexOf(player), 1);

  }

  getEmptyFrames(size): Array<Frame>
  {
    let frames = new Array<Frame>();
    for(let i=0;i<size;i++)
    {
      let frame = new Frame();
      frames.push(frame);
    }
    return frames;
  }

  startGame(): void {
    this.scoringService.startGame(this.game).subscribe((response) => {
      this.game.gameId=response.gameId;
    }, (err) => {
      alert(err.message)
    });
  }

  playRoll(): void
  {
    let nextPins = new Throw();
    nextPins.gameId=this.game.gameId;

    nextPins.pins=4;
    this.scoringService.playGame(nextPins).subscribe((response) => {
      this.game =response;
    }, (err) => {
      alert(err.message)
    });
  }
}
