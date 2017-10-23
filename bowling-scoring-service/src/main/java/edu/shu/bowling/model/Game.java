package edu.shu.bowling.model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private String gameId;

    @Column(name = "lane_id")
    private int laneId;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTimeDate;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTimeDate() {
        return endTimeDate;
    }

    public void setEndTimeDate(Date endTimeDate) {
        this.endTimeDate = endTimeDate;
    }

}
