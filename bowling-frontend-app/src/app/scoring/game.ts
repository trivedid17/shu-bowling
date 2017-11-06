import {Player} from "./player";

export class Game{
  gameId: string;
  laneId: number;
  currentPlayer: Player;
  currentFrameNo: number;
  players= new Array<Player>();
  gameStatus='ACTIVE';
}
