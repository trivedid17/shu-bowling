package edu.shu.bowling.score.dto.input;

import edu.shu.bowling.score.dto.PlayerDto;

import java.util.ArrayList;
import java.util.List;

public class StartGameRequest {

    private List<PlayerDto> players = new ArrayList<>();

    private int laneId;

    public List<PlayerDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDto> players) {
        this.players = players;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }


}

