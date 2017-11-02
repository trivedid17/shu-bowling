package edu.shu.bowling.score.controller;


import edu.shu.bowling.score.dto.FrameDto;
import edu.shu.bowling.score.dto.PlayerDto;
import edu.shu.bowling.score.dto.ScoreBoardDto;
import edu.shu.bowling.score.dto.input.GamePlayRequest;
import edu.shu.bowling.score.dto.input.StartGameRequest;
import edu.shu.bowling.score.dto.output.StartGameResponse;
import edu.shu.bowling.score.model.Frame;
import edu.shu.bowling.score.model.Game;
import edu.shu.bowling.score.model.LastFrame;
import edu.shu.bowling.score.model.Player;
import edu.shu.bowling.score.service.ScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {


    private final ScoreService scoreService;

    public GameController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public StartGameResponse startGame(@RequestBody StartGameRequest input) {
        StartGameResponse output = new StartGameResponse();
        Game game = new Game();
        game.setLaneId(input.getLaneId());
        for(PlayerDto inputPlayer : input.getPlayers()){
            Player player = new Player();
            player.setPlayerName(inputPlayer.getName());
            game.getPlayers().add(player);
        }
        String result = scoreService.startGame(game);
        output.setGameId(result);
        return output;
    }

    @RequestMapping(value = "/score", method = RequestMethod.POST)
    @ResponseBody
    public ScoreBoardDto calculateScore(@RequestBody GamePlayRequest input) {

        Game game= scoreService.computeScore(input.getGameId(), input.getPins());
        ScoreBoardDto scoreboard = new ScoreBoardDto();
        scoreboard.setGameId(game.getGameId());
        scoreboard.setGameStatus(game.getStatus().toString());

        for(Player player : game.getPlayers()){
            PlayerDto playerDto = new PlayerDto();
            playerDto.setName(player.getPlayerName());
            playerDto.setRank(player.getSeqNo());
            playerDto.setScore(player.getScore());

            for(Frame frame: player.getFrames()){
                FrameDto frameDto = new FrameDto();
                frameDto.setFrameNo(frame.getSeqNo());
                frameDto.setRoll1(frame.getRoll1());
                frameDto.setRoll2(frame.getRoll2());
                if(frame instanceof LastFrame)
                {
                    frameDto.setRoll3(((LastFrame) frame).getRoll3());
                }
                playerDto.getFrames().add(frameDto);
            }
            scoreboard.getPlayers().add(playerDto);
        }
        return scoreboard;
    }

}