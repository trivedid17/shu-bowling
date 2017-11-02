package edu.shu.bowling.score.service;

        import edu.shu.bowling.score.model.Game;

public interface ScoreService {
    Game computeScore(Game game);

    Game computeScore(String gameId, int pins);

    String startGame(Game input);
}