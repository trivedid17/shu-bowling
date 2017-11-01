package edu.shu.bowling.score.service;

import edu.shu.bowling.score.exception.ValidationException;
import edu.shu.bowling.score.model.*;
import edu.shu.bowling.score.repository.GameRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {


    private final GameRepository gameRepository;

    public ScoreServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    @Override
    public String startGame(Game game) {
        String result;
        try {
            game.setStartTime(new Date());
            byte playerCount = 1;
            for (Player player : game.getPlayers()) {
                if (playerCount == 1) {
                    game.setCurrentPlayer(player);
                }
                player.setSeqNo(playerCount++);
            }
            game.setCurrentFrameNo(1);
            game.setStartTime(new Date());
            result = gameRepository.save(game).getGameId();
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
    public Game computeScore(Game game) {

        if (game.getPlayers().size() == 0) {
            throw new ValidationException("Game should have at least one player");
        }

        List<Player> players = game.getPlayers();
        for (Player player : players) {
            player.setScore(calculateTotalScore(player));
        }

        return game;
    }

    @Override
    public Game computeScore(String gameId, int pins) {
        Game game = gameRepository.findOne(gameId);
        validateGame(game, gameId);

        game = updateGame(game, pins);
        return computeScore(game);
    }

    private void validateGame(Game game, String gameId) {
        if (game == null) {
            throw new ValidationException("Game with gameId=" + gameId + " does not exist");
        }

        if (game.getStatus() != GameStatus.ACTIVE) {
            throw new ValidationException("Game with gameId=" + game.getGameId() + " is " + game.getStatus().toString());
        }
    }

    private Game updateGame(Game game, int pins) {
        Player player = game.getCurrentPlayer();
        if (game.getCurrentFrameNo() < 10) {
            setFrameScore(game, player, pins);
        } else {
            setLastFrameScore(game, player, pins);
        }
        return gameRepository.saveAndFlush(game);
    }

    private void setFrameScore(Game game, Player player, int pins) {

        Frame frame = findCurrentFrame(player, game.getCurrentFrameNo());
        boolean moveToNextPlayer = (pins == 10) || frame != null;
        if (frame == null) {
            frame = new Frame();
            frame.setSeqNo(game.getCurrentFrameNo());
            player.getFrames().add(frame);
            frame.setRoll1(pins);
        } else {
            if (frame.getRoll1() + pins > 10) {
                throw new ValidationException("There should not be more 10 pins in a single frame");
            }
            frame.setRoll2(pins);
        }

        if (moveToNextPlayer) {
            Player nextPlayer = game.getNextPlayer();
            if (nextPlayer.getSeqNo() == 1) {
                game.setCurrentFrameNo(game.getCurrentFrameNo() + 1);
            }
            game.setCurrentPlayer(nextPlayer);
        }
    }


    private void setLastFrameScore(Game game, Player player, int pins) {
        Frame frame = findCurrentFrame(player, game.getCurrentFrameNo());

        if (frame == null) {
            frame = new LastFrame();
            frame.setSeqNo(game.getCurrentFrameNo());
            player.getFrames().add(frame);
            frame.setRoll1(pins);
        } else {

            if (frame.rollsPlayedCount() > 0) {

                if (frame.rollsPlayedCount() == 1) {
                    int total = frame.getRoll1() + pins;
                    if (total > 10 && frame.getRoll1() != 10) {
                        throw new ValidationException("If there is no strikes in first throw of last frame then sum of two frame should not exceed 10 ");

                    }
                    frame.setRoll2(pins);
                }else if (frame.rollsPlayedCount() == 2) {
                    int total = frame.getRoll1() + frame.getRoll2() + pins;
                    if (total > 10 && !(frame.getRoll1() == 10 || frame.getRoll2() == 10 || ((LastFrame) frame).getRoll3() == 10)) {
                        throw new ValidationException("If there is no strikes in any throws of last frame then sum of three frame should not exceed 10 ");
                    }
                    ((LastFrame) frame).setRoll3(pins);
                }
            }
        }

        boolean moveToNextPlayer = (frame.hasSpare() || frame.hasStrike()) && frame.rollsPlayedCount() == 3 || !(frame.hasSpare() || frame.hasStrike()) && frame.rollsPlayedCount() == 2;
        if (moveToNextPlayer) {
            if(game.isCurrentPlayerLastPlayer())
            {
                game.complete();
            }
            game.setCurrentPlayer(game.getNextPlayer());
        }
    }


    private Frame findCurrentFrame(Player player, int frameNo) {
        Frame frame = null;
        for (Frame f : player.getFrames()) {
            if (f.getSeqNo() == frameNo) {
                frame = f;
                break;
            }
        }
        return frame;
    }


    private int calculateTotalScore(Player player) {
        player.getFrames().sort(Comparator.comparingInt(Frame::getSeqNo));
        List<Frame> frames = player.getFrames();
        int frameCount = frames.size();
        int totalScore = 0;
        for (int i = 0; i < frameCount; i++) {
            Frame frame = frames.get(i);
            Frame nextFrame = i + 1 < frameCount ? frames.get(i + 1) : null;
            Frame thirdFrame = i + 2 < frameCount ? frames.get(i + 2) : null;

            int frameScore = calculateFrameScore(frame, nextFrame, thirdFrame);
            if (frameScore > 0) {
                totalScore = totalScore + frameScore;
            }
        }
        return totalScore;
    }

    private int calculateFrameScore(Frame frame, Frame nextFrame, Frame thirdFrame) {
        if (!(frame.hasStrike() || frame.hasSpare())) {
            return frame.getRoll1() + frame.getRoll2();
        }

        if (frame.isLastFrame() && frame.rollsPlayedCount() == 3) {
            return frame.getRoll1() + frame.getRoll2() + ((LastFrame) frame).getRoll3();
        }

        if (frame.isLastFrame()) {
            return -1;
        }

        if ((frame.hasSpare() || frame.hasStrike()) && nextFrame == null) {
            return -1;

        }

        if (frame.hasSpare() && nextFrame.rollsPlayedCount() > 0) {
            return 10 + nextFrame.getRoll1();
        }
        if (frame.hasStrike() && !nextFrame.hasStrike()) {
            if (nextFrame.rollsPlayedCount() > 1) {
                return 10 + nextFrame.getRoll1() + nextFrame.getRoll2();
            }
            return -1;
        }

        if (frame.hasStrike() && nextFrame.hasStrike()) {
            if (nextFrame.isLastFrame()) {
                if (nextFrame.rollsPlayedCount() > 1) {
                    return 10 + nextFrame.getRoll1() + nextFrame.getRoll2();
                }
                return -1;
            }

            if (thirdFrame != null && thirdFrame.rollsPlayedCount() > 0) {
                return 10 + nextFrame.getRoll1() + thirdFrame.getRoll1();
            }
        }

        return -1;
    }

    private void setFinished(Game game) {
        game.setEndTimeDate(new Date());
        game.setStatus(GameStatus.COMPLETED);
    }
}
