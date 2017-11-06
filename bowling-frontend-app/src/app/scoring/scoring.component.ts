import {Component, OnInit} from '@angular/core';
import {Game} from './game';
import {ScoringService} from '../service/scoring.service';
import {Player} from "./player";
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
    for (let i = 0; i < 10; i++) {
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

  getEmptyFrames(size): Array<Frame> {
    let frames = new Array<Frame>();
    for (let i = 0; i < size; i++) {
      let frame = new Frame();
      frames.push(frame);
    }
    return frames;
  }

  startGame(): void {
    this.scoringService.startGame(this.game).subscribe((response) => {
      this.game.gameId = response.gameId;
      this.game.currentPlayer = this.game.players[0];
      this.game.currentFrameNo = 1;
    }, (err) => {
      alert(err.message)
    });
  }

  playRoll(): void {
    let nextPins = new Throw();
    let player = this.getCurrentPlayer();
    let frame = this.getCurrentFrame(player);

    if (this.game.currentFrameNo < 9) {
      if (frame.roll1 > -1) {
        nextPins.pins = this.getRandomInt(0, (10 - frame.roll1));
      } else {
        nextPins.pins = this.getRandomInt(0, 10);
      }
    } else {
      if (frame.roll1 > -1 && frame.roll1 != 10 && frame.roll2 < 0) {
        nextPins.pins = this.getRandomInt(0, (10 - frame.roll1));
      } else {
        nextPins.pins = this.getRandomInt(0, 10);
      }
    }

    nextPins.gameId = this.game.gameId;


    this.scoringService.playGame(nextPins).subscribe((response) => {
      this.game = response;
    }, (err) => {
      alert(err.message)
    });
  }


  getCurrentPlayer(): Player {
    for (let i = 0; i < this.game.players.length; i++) {
      if (this.game.players[i].rank == this.game.currentPlayer.rank) {
        return this.game.players[i];
      }
    }
    return this.game.players[0];
  }


  getCurrentFrame(player): Frame {
    for (let i = 0; i < player.frames.length; i++) {
      if (player.frames[i].frameNo == this.game.currentFrameNo) {
        return player.frames[i];
      }
    }
    return new Frame();
  }

  getRandomInt(min, max): number {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }
}
