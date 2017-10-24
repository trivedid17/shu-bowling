package edu.shu.bowling.service;

import edu.shu.bowling.model.Game;
import edu.shu.bowling.rest.input.CalculateScoreInput;
import org.springframework.ui.Model;

public interface ScoreService {

     String startGame(Game game);

     Game calculteScore(CalculateScoreInput throwPins);
}
