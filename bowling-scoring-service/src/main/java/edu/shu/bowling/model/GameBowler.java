package edu.shu.bowling.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "game_bowler")
public class GameBowler implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "bowler_id")
    private Bowler bowler;

    @Column(name = "seq_no")
    @Max(7)
    @Min(1)
    private byte seqNo;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Bowler getBowler() {
        return bowler;
    }

    public void setBowler(Bowler bowler) {
        this.bowler = bowler;
    }

    public byte getSeqNo() {

        return seqNo;
    }

    public void setSeqNo(byte seqNo) {

        this.seqNo = seqNo;
    }
}
