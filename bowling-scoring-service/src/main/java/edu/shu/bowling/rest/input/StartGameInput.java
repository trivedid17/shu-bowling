package edu.shu.bowling.rest.input;

import edu.shu.bowling.model.Bowler;

import java.util.HashSet;
import java.util.Set;

public class StartGameInput {

    private Set<Bowler> bowlers = new HashSet<>();
    private int laneId;

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public Set<Bowler> getBowlers() {
        return bowlers;
    }

    public void setBowlers(Set<Bowler> bowlers) {
        this.bowlers = bowlers;
    }

}
