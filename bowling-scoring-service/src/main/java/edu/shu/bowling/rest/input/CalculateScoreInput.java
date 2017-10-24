package edu.shu.bowling.rest.input;

public class CalculateScoreInput {

    private String gameId;
    private byte pins;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public byte getPins() {
        return pins;
    }

    public void setPins(byte pins) {
        this.pins = pins;
    }
}
