package edu.shu.bowling.service;

import edu.shu.bowling.model.Game;
import edu.shu.bowling.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public String startGame(Game game) {

        return gameRepository.saveAndFlush(game).getGameId();
    }
}
