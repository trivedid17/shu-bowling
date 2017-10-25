package edu.shu.bowling.service;

import edu.shu.bowling.model.Game;
import edu.shu.bowling.rest.input.CalculateScoreInput;
import edu.shu.bowling.rest.input.StartGameInput;
import org.springframework.ui.Model;

public interface ScoreService {

     String startGame(StartGameInput input);

     Game calculteScore(CalculateScoreInput throwPins);
}
