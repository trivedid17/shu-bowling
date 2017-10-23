package edu.shu.bowling.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity
public class Frame {

    @EmbeddedId
    private FrameId frameId;

    @Column(name = "throw1")
    private byte throw1;

    @Column(name = "throw2")
    private byte throw2;

    public FrameId getFrameId() {
        return frameId;
    }

    public void setFrameId(FrameId frameId) {
        this.frameId = frameId;
    }

    public byte getThrow1() {
        return throw1;
    }

    public void setThrow1(byte throw1) {
        this.throw1 = throw1;
    }

    public byte getThrow2() {
        return throw2;
    }

    public void setThrow2(byte throw2) {
        this.throw2 = throw2;
    }


}
