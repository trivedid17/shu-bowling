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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name ="game_bowler", joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "game_id"), inverseJoinColumns =@JoinColumn(name = "bowler_id", referencedColumnName = "bowler_id"))
    @Size(min=1, max=7, message="The number of players in a game must be between {min} and {max}")
    private Set<Bowler> bowlers;

    public Game(){
        bowlers = new HashSet<Bowler>();
    }

    public Set<Bowler> getBowlers() {
        return bowlers;
    }

    public void setBowlers(Set<Bowler> bowlers) {
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

}
