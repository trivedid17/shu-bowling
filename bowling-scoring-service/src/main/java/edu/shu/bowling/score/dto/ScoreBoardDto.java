package edu.shu.bowling.score.dto;

import edu.shu.bowling.score.model.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardDto {
    String gameId;
    String gameStatus;
    List<PlayerDto> players;
    private PlayerDto currentPlayer;
    private int currentFrameNo;

    public ScoreBoardDto() {
        players = new ArrayList<>();
    }

    public PlayerDto getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerDto currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentFrameNo() {
        return currentFrameNo;
    }

    public void setCurrentFrameNo(int currentFrameNo) {
        this.currentFrameNo = currentFrameNo;
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
