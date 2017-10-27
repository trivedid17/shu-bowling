package edu.shu.bowling.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity
public class LastFrame {

    @EmbeddedId
    private FrameId frameId;

    @Column(name = "throw1")
    private byte throw1 = -1;

    @Column(name = "throw2")
    private byte throw2 = -1;

    @Column(name = "throw3")
    private byte throw3 = -1;

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

    public byte getThrow3() {
        return throw3;
    }

    public void setThrow3(byte throw3) {
        this.throw3 = throw3;
    }

}
