package edu.shu.bowling.score.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String gameId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GameStatus status;

    private int laneId;

    @OneToMany(cascade = CascadeType.ALL)
    @Size(max = 6, min = 1, message = "The number of players in a game must be between {min} and {max}")
    private List<Player> players;

    private Date startTime;

    @OneToOne(cascade = CascadeType.ALL)
    private Player currentPlayer;
    private int currentFrameNo;
    private Date endTimeDate;

    public Game() {
        this.players = new ArrayList<>();
        status = GameStatus.ACTIVE;
    }

    public Date getEndTimeDate() {
        return endTimeDate;
    }

    public void setEndTimeDate(Date endTimeDate) {
        this.endTimeDate = endTimeDate;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getCurrentFrameNo() {
        return currentFrameNo;
    }

    public void setCurrentFrameNo(int currentFrameNo) {
        this.currentFrameNo = currentFrameNo;
    }

    public boolean isCurrentPlayerLastPlayer() {
        for (Player player : getPlayers()) {

            if (player.getSeqNo() == getPlayers().size()) {
                return true;
            }
        }
        return false;
    }

    private Player getFirstPlayer() {
        for (Player player : getPlayers()) {

            if (player.getSeqNo() == 1) {
                return player;
            }
        }
        return null;
    }

    public Player getNextPlayer() {
        players.sort(Comparator.comparingInt(Player::getSeqNo));
        if (isCurrentPlayerLastPlayer()) {
            return getFirstPlayer();
        }
        for (Player player : getPlayers()) {

            if (player.getSeqNo() == getCurrentPlayer().getSeqNo() + 1) {
                return player;
            }
        }
        return null;
    }

    public void complete() {
        setEndTimeDate(new Date());
        setStatus(GameStatus.COMPLETED);
    }
}
