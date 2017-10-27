package edu.shu.bowling.model;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "frame_no")
    private byte currentFrameNo = 1;

    @Column(name = "bowler_id")
    private String currentBowler;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GameStatus status = GameStatus.ACTIVE;


    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(max = 6, min = 1, message = "The number of players in a game must be between {min} and {max}")
    private Set<GameBowler> bowlers;

    public Game() {
        this.bowlers = new HashSet<>();
    }

    public String getCurrentBowler() {
        return currentBowler;
    }

    public void setCurrentBowler(String currentBowler) {
        this.currentBowler = currentBowler;
    }

    public byte getCurrentFrameNo() {
        return currentFrameNo;
    }

    public void setCurrentFrameNo(byte currentFrameNo) {
        this.currentFrameNo = currentFrameNo;
    }

    public Set<GameBowler> getBowlers() {
        return bowlers;
    }

    public void setBowlers(Set<GameBowler> bowlers) {
        this.bowlers = bowlers;
    }

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

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

}
