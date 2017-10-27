package edu.shu.bowling.service;

import edu.shu.bowling.model.Game;
import edu.shu.bowling.rest.input.CalculateScoreInput;
import edu.shu.bowling.rest.input.StartGameInput;

public interface ScoreService {

    String startGame(StartGameInput input);

    Game calculateScore(CalculateScoreInput throwPins);
}
