package edu.shu.bowling.score.dto;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardDto {
    String gameId;
    String gameStatus;
    List<PlayerDto> players;


    public ScoreBoardDto(){
        players = new ArrayList<>();
    }
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<PlayerDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDto> players) {
        this.players = players;
    }

}
