package edu.shu.bowling.score.dto;

import java.util.ArrayList;
import java.util.List;

public class PlayerDto {
    List<FrameDto> frames;
    private String name;
    private int rank;
    private int score;

    public PlayerDto() {
        frames = new ArrayList<>();
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FrameDto> getFrames() {
        return frames;
    }

    public void setFrames(List<FrameDto> frames) {
        this.frames = frames;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
