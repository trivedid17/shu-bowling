package edu.shu.bowling.model;

import javax.persistence.*;

@Entity
public class Bowler {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bowler_id")
    private String bowlerId;

    @Column(name = "name")
    private String name;

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
