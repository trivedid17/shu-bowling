package edu.shu.bowling.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class FrameId implements Serializable {
    private static final long serialVersionUID = 1L;


    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bowler_id")
    private Bowler bowler;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_no")
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
