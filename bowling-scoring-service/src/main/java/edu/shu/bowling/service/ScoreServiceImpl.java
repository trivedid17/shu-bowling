package edu.shu.bowling.service;

import edu.shu.bowling.common.ValidationException;
import edu.shu.bowling.model.Game;
import edu.shu.bowling.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;

@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public String startGame(Game game) {

        String result;
        try {
            game.setStartTime(new Date());
            result= gameRepository.saveAndFlush(game).getGameId();

        }catch (ConstraintViolationException exception)
        {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> violation : exception.getConstraintViolations())
            {
                sb.append(violation.getMessage() + "\n");
            }
            throw new ValidationException(sb.toString());
        }
        return result;
    }
}
