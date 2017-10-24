package edu.shu.bowling.service;

import edu.shu.bowling.common.GameNotActiveException;
import edu.shu.bowling.common.GameNotExistException;
import edu.shu.bowling.common.ValidationException;
import edu.shu.bowling.model.Game;
import edu.shu.bowling.model.GameStatus;
import edu.shu.bowling.repository.GameRepository;
import edu.shu.bowling.rest.input.CalculateScoreInput;
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
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ValidationException(sb.toString());
        }
        return result;
    }

    @Override
    public Game calculteScore(CalculateScoreInput throwPins) {

        Game result;

       result= gameRepository.findOne(throwPins.getGameId());
       if(result == null)
       {
           throw new GameNotExistException("Game with gameId="+throwPins.getGameId()+" does not exist");
       }

       if(result.getStatus() != GameStatus.ACTIVE)
       {
           throw new GameNotActiveException("Game with gameId="+throwPins.getGameId()+" is not active");
       }

       return result;
    }
}
