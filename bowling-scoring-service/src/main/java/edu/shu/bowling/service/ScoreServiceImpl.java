package edu.shu.bowling.service;

import edu.shu.bowling.common.GameNotActiveException;
import edu.shu.bowling.common.GameNotExistException;
import edu.shu.bowling.common.ValidationException;
import edu.shu.bowling.model.*;
import edu.shu.bowling.repository.BowlerRepositroy;
import edu.shu.bowling.repository.FrameRepository;
import edu.shu.bowling.repository.GameRepository;
import edu.shu.bowling.rest.input.CalculateScoreInput;
import edu.shu.bowling.rest.input.StartGameInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private BowlerRepositroy bowlerRepositroy;

    @Autowired
    private FrameRepository frameRepository;

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

        Game game;

        game= gameRepository.findOne(throwPins.getGameId());
       if(game == null)
       {
           throw new GameNotExistException("Game with gameId="+throwPins.getGameId()+" does not exist");
       }

       if(game.getStatus() != GameStatus.ACTIVE)
       {
           throw new GameNotActiveException("Game with gameId="+throwPins.getGameId()+" is not active");
       }


       //if it is first throw for current player then create new frame else create new one
       /*Bowler bowler = bowlerRepositroy.findOne(game.getCurrentBowler());

        FrameId frameId = new FrameId();
        frameId.setGame(game);
        frameId.setBowler(bowler);
        frameId.setSeqNo(game.getCurrentFrameNo());
        Frame frame =  frameRepository.findOne(frameId);

        Map<Byte,GameBowler> bowlersListBySeqNo = new HashMap<>();
        Map<String,GameBowler> bowlersListById = new HashMap<>();
        for(GameBowler gameBowler : game.getBowlers())
        {
            bowlersListBySeqNo.put(gameBowler.getSeqNo(),gameBowler);
            bowlersListById.put(gameBowler.getBowler().getBowlerId(),gameBowler);
        }

        boolean isNextBowler = false;
        if(frame != null)
        {

        }

        if(isNextBowler)
        {
            frameId = new FrameId();
            frameId.setGame(game);
            //get NextBowler
            int totalPlayers = game.getBowlers().size();


            frameId.setBowler(bowler);

        }
*/


       return game;
    }
}
