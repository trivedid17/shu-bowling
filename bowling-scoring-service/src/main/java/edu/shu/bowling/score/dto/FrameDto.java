package edu.shu.bowling.score.dto;

public class FrameDto {
    int roll1;
    int roll2;
    int roll3;
    int frameNo;

    public int getFrameNo() {
        return frameNo;
    }

    public void setFrameNo(int frameNo) {
        this.frameNo = frameNo;
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

    public int getRoll3() {
        return roll3;
    }

    public void setRoll3(int roll3) {
        this.roll3 = roll3;
    }
}
