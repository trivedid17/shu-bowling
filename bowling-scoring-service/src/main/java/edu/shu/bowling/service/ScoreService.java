package edu.shu.bowling.service;

import edu.shu.bowling.model.Game;
import org.springframework.ui.Model;

public interface ScoreService {

    public String startGame(Game game);
}
