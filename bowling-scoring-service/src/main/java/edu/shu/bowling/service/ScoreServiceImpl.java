package edu.shu.bowling.service;

import edu.shu.bowling.common.GameNotActiveException;
import edu.shu.bowling.common.GameNotExistException;
import edu.shu.bowling.common.ValidationException;
import edu.shu.bowling.model.Bowler;
import edu.shu.bowling.model.Game;
import edu.shu.bowling.model.GameBowler;
import edu.shu.bowling.model.GameStatus;
import edu.shu.bowling.repository.BowlerRepositroy;
import edu.shu.bowling.repository.GameRepository;
import edu.shu.bowling.rest.input.CalculateScoreInput;
import edu.shu.bowling.rest.input.StartGameInput;
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

    @Autowired
    private BowlerRepositroy bowlerRepositroy;

    @Override
    public String startGame(StartGameInput input) {

        String result;
        try {

            Game game = new Game();
            game.setLaneId(input.getLaneId());
            game.setStartTime(new Date());

            byte playerCount=1;
            for(Bowler bowler: input.getBowlers())
            {
                bowlerRepositroy.save(bowler);
                GameBowler gameBowler=new GameBowler();
                gameBowler.setGame(game);
                gameBowler.setBowler(bowler);
                gameBowler.setSeqNo(playerCount++);
                game.getBowlers().add(gameBowler);
            }

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
