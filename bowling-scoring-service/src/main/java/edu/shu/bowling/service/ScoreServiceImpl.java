package edu.shu.bowling.service;

import edu.shu.bowling.common.GameNotActiveException;
import edu.shu.bowling.common.GameNotExistException;
import edu.shu.bowling.common.ValidationException;
import edu.shu.bowling.model.*;
import edu.shu.bowling.repository.BowlerRepository;
import edu.shu.bowling.repository.FrameRepository;
import edu.shu.bowling.repository.GameRepository;
import edu.shu.bowling.repository.LastFrameRepository;
import edu.shu.bowling.rest.input.CalculateScoreInput;
import edu.shu.bowling.rest.input.StartGameInput;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {


    private final GameRepository gameRepository;


    private final BowlerRepository bowlerRepository;


    private final FrameRepository frameRepository;


    private final LastFrameRepository lastFrameRepository;

    private Map<Byte, GameBowler> bowlersListBySeqNo;
    private Map<String, GameBowler> bowlersListById;

    public ScoreServiceImpl(GameRepository gameRepository, BowlerRepository bowlerRepository, FrameRepository frameRepository, LastFrameRepository lastFrameRepository) {
        this.gameRepository = gameRepository;
        this.bowlerRepository = bowlerRepository;
        this.frameRepository = frameRepository;
        this.lastFrameRepository = lastFrameRepository;
    }

    @Override
    public String startGame(StartGameInput input) {

        String result;
        try {

            Game game = new Game();
            game.setLaneId(input.getLaneId());
            game.setStartTime(new Date());

            byte playerCount = 1;
            for (Bowler bowler : input.getBowlers()) {
                bowlerRepository.save(bowler);
                if (playerCount == 1) {
                    game.setCurrentBowler(bowler.getBowlerId());
                }
                GameBowler gameBowler = new GameBowler();
                gameBowler.setGame(game);
                gameBowler.setBowler(bowler);
                gameBowler.setSeqNo(playerCount++);
                game.getBowlers().add(gameBowler);
            }

            game.setStartTime(new Date());
            result = gameRepository.saveAndFlush(game).getGameId();

        } catch (ConstraintViolationException exception) {

            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ValidationException(sb.toString());
        }
        return result;
    }

    @Override
    public Game calculateScore(CalculateScoreInput throwPins) {

        Game game = gameRepository.findOne(throwPins.getGameId());
        validateGame(game, throwPins.getGameId());
        setGameBowlerMappingList(game);

        Bowler bowler = findCurrentBowler(game);

        if (game.getCurrentFrameNo() < 10) {
            setFrameScore(game, bowler, throwPins.getPins());
        } else {
            setLastFrameScore(game, bowler, throwPins.getPins());
        }
        return gameRepository.saveAndFlush(game);
    }


    private void validateGame(Game game, String gameId) {
        if (game == null) {
            throw new GameNotExistException("Game with gameId=" + gameId + " does not exist");
        }

        if (game.getStatus() != GameStatus.ACTIVE) {
            throw new GameNotActiveException("Game with gameId=" + game.getGameId() + " is " + game.getStatus().toString());
        }
    }

    private Bowler findCurrentBowler(Game game) {
        return bowlerRepository.findOne(game.getCurrentBowler());
    }

    private Frame findCurrentFrame(Game game, Bowler bowler) {
        FrameId frameId = createFrameId(game, bowler);
        return frameRepository.findOne(frameId);
    }

    private LastFrame findCurrentLastFrame(Game game, Bowler bowler) {
        FrameId frameId = createFrameId(game, bowler);
        return lastFrameRepository.findOne(frameId);
    }

    private FrameId createFrameId(Game game, Bowler bowler) {
        FrameId frameId = new FrameId();
        frameId.setGame(game);
        frameId.setBowler(bowler);
        frameId.setSeqNo(game.getCurrentFrameNo());
        return frameId;
    }

    private Frame createNewFrame(Game game, Bowler bowler) {
        FrameId frameId = new FrameId();
        frameId.setGame(game);
        frameId.setBowler(bowler);
        frameId.setSeqNo(game.getCurrentFrameNo());
        Frame frame = new Frame();
        frame.setFrameId(frameId);
        return frame;
    }

    private LastFrame createNewLastFrame(Game game, Bowler bowler) {
        FrameId frameId = new FrameId();
        frameId.setGame(game);
        frameId.setBowler(bowler);
        frameId.setSeqNo(game.getCurrentFrameNo());
        LastFrame frame = new LastFrame();
        frame.setFrameId(frameId);
        return frame;
    }

    private void setFinished(Game game) {
        game.setEndTimeDate(new Date());
        game.setStatus(GameStatus.FINISHED);
    }

    private void setFrameScore(Game game, Bowler bowler, byte pins) {
        Frame frame = findCurrentFrame(game, bowler);

        boolean moveToNextPlayer = (pins == 10) || frame != null;

        if (frame == null) {
            frame = createNewFrame(game, bowler);
            frame.setThrow1(pins);
        } else {
            if (frame.getThrow1() + pins > 10) {
                throw new ValidationException("There should not be more 10 pins in a single frame");
            }
            frame.setThrow2(pins);
        }

        if (moveToNextPlayer) {
            String nextBowlerId = getNextBowlerId(game);
            if (isLastBowler(game)) {
                game.setCurrentFrameNo((byte) (game.getCurrentFrameNo() + 1));
            }
            game.setCurrentBowler(nextBowlerId);
        }
        frameRepository.save(frame);
    }


    private void setLastFrameScore(Game game, Bowler bowler, byte pins) {
        LastFrame frame = findCurrentLastFrame(game, bowler);

        if (frame == null) {
            frame = createNewLastFrame(game, bowler);
            frame.setThrow1(pins);
        } else {
            int currentThrowNo = calCurrentThrow(frame);
            if (currentThrowNo > 0) {

                if (currentThrowNo == 1) {
                    frame.setThrow1(pins);
                }

                if (currentThrowNo == 2) {
                    int total = frame.getThrow1() + pins;
                    if (total > 10 && frame.getThrow1() != 10) {
                        throw new ValidationException("If there is no strikes in first throw of last frame then sum of two frame should not exceed 10 ");

                    }
                    frame.setThrow2(pins);
                }

                if (currentThrowNo == 3) {
                    int total = frame.getThrow1() + frame.getThrow2() + pins;
                    if (total > 10 && !(frame.getThrow1() == 10 || frame.getThrow2() == 10 || frame.getThrow2() == 10)) {
                        throw new ValidationException("If there is no strikes in any throws of last frame then sum of three frame should not exceed 10 ");
                    }
                    frame.setThrow3(pins);
                }
            }
        }

        boolean moveToNextPlayer = calCurrentThrow(frame) == 0;
        if (moveToNextPlayer) {
            String nextBowlerId = getNextBowlerId(game);
            if (isLastBowler(game)) {
                setFinished(game);
            }
            game.setCurrentBowler(nextBowlerId);
        }
        lastFrameRepository.save(frame);
    }

    private void setGameBowlerMappingList(Game game) {
        bowlersListBySeqNo = new HashMap<>();
        bowlersListById = new HashMap<>();
        for (GameBowler gameBowler : game.getBowlers()) {
            bowlersListBySeqNo.put(gameBowler.getSeqNo(), gameBowler);
            bowlersListById.put(gameBowler.getBowler().getBowlerId(), gameBowler);
        }
    }


    private boolean isLastBowler(Game game) {
        GameBowler gameBowler = bowlersListById.get(game.getCurrentBowler());
        return gameBowler.getSeqNo() == game.getBowlers().size();
    }

    private String getNextBowlerId(Game game) {
        String nextBowlerId;
        if (isLastBowler(game)) {
            nextBowlerId = getBowlerIdByIndex((byte) 1);

        } else {
            byte currentBowlerIndex = getCurrentBowlerIndex(game);
            nextBowlerId = getBowlerIdByIndex((byte) (currentBowlerIndex + 1));
        }
        return nextBowlerId;
    }

    private String getBowlerIdByIndex(byte index) {

        return bowlersListBySeqNo.get(index).getBowler().getBowlerId();
    }

    private byte getCurrentBowlerIndex(Game game) {
        return bowlersListById.get(game.getCurrentBowler()).getSeqNo();
    }

    private int calCurrentThrow(LastFrame frame) {
        if (frame.getThrow1() == -1) {
            return 1;
        }

        if (frame.getThrow2() == -1) {
            return 2;
        }

        if (frame.getThrow2() > -1 && frame.getThrow1() + frame.getThrow2() < 10) {
            return 0;
        }

        if (frame.getThrow2() > -1 && frame.getThrow1() + frame.getThrow2() > 10 && frame.getThrow3() == -1) {
            return 3;
        }
        return 0;
    }


}
