package edu.shu.bowling.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Bowler {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bowler_id")
    private String bowlerId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bowler")
    @JsonIgnore
    private Set<GameBowler> games;

    public Set<GameBowler> getGames() {
        return games;
    }

    public void setGames(Set<GameBowler> games) {
        this.games = games;
    }

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
