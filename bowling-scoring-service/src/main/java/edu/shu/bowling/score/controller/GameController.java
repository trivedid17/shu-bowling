package edu.shu.bowling.score.controller;


import edu.shu.bowling.score.dto.input.PlayerDto;
import edu.shu.bowling.score.dto.input.StartGameRequest;
import edu.shu.bowling.score.dto.input.StartGameResponse;
import edu.shu.bowling.score.model.Game;
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

   // @RequestMapping(value = "/score", method = RequestMethod.POST)
    //@ResponseBody
    //public ScoreBoard calculateScore(@RequestBody CalculateScoreInput pins) {

      //  return scoreService.calculateScore(pins);
    //}

}