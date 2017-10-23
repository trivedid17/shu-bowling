package edu.shu.bowling.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FrameId implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "game_id", nullable = false)
    private String gameId;


    @Column(name = "bowler_id", nullable = false)
    private String bowlerId;
    @Column(name = "seq_no")
    private byte seqNo;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

    public byte getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(byte seqNo) {
        this.seqNo = seqNo;
    }
}
