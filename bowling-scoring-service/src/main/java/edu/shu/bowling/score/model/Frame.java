package edu.shu.bowling.score.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class  Frame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int frameId;
    private int roll1;
    private int roll2;
    private int seqNo;



    public Frame()
    {
        roll1 = -1;
        roll2 = -1;
    }
    public int getRoll1() {
        return roll1;
    }

    public void setRoll1(int roll1) {
        this.roll1 = roll1;
    }

    public int getRoll2() {
        return roll2;
    }

    public void setRoll2(int roll2) {
        this.roll2 = roll2;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public boolean isLastFrame(){
        return false;
    }

    public boolean hasStrike() {
        return roll1 == 10;
    }

    public int rollsPlayedCount() {
        if (roll1 == -1) {
            return 0;
        }
        if (roll2 == -1) {
            return 1;
        }
        return 2;
    }

    public boolean hasSpare() {
        return rollsPlayedCount() > 1 && (roll1 + roll2) == 10;
    }

    public int getFrameId() {
        return frameId;
    }

    public void setFrameId(int frameId) {
        this.frameId = frameId;
    }
}
