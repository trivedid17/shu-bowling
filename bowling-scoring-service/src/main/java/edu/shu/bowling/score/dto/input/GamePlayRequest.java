package edu.shu.bowling.score.dto.input;

public class GamePlayRequest {
    int pins;
    String gameId;

    public int getPins() {
        return pins;
    }

    public void setPins(int pins) {
        this.pins = pins;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
