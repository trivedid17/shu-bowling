package edu.shu.bowling.score.model;

import javax.persistence.Entity;

@Entity(name = "LastFrame")
public class LastFrame extends  Frame {

    private int roll3;

   public LastFrame(){
        super();
        roll3=-1;
    }

    public int getRoll3() {
        return roll3;
    }

    public void setRoll3(int roll3) {
        this.roll3 = roll3;
    }

    public boolean isLastFrame(){
        return true;
    }

    @Override
    public int rollsPlayedCount() {
        if (getRoll1() == -1) {
            return 0;
        }
        if (getRoll2() == -1) {
            return 1;
        }

        if (roll3 == -1) {
            return 2;
        }
        return 3;
    }
}
