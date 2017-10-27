package edu.shu.bowling.controller;

import edu.shu.bowling.model.Game;
import edu.shu.bowling.rest.input.CalculateScoreInput;
import edu.shu.bowling.rest.input.StartGameInput;
import edu.shu.bowling.rest.output.GameStartOutput;
import edu.shu.bowling.service.ScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {


    private final ScoreService scoreService;

    public GameController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public GameStartOutput startGame(@RequestBody StartGameInput game) {
        GameStartOutput output = new GameStartOutput();
        String result = scoreService.startGame(game);
        output.setGameId(result);
        return output;
    }

    @RequestMapping(value = "/score", method = RequestMethod.POST)
    @ResponseBody
    public Game calculateScore(@RequestBody CalculateScoreInput pins) {

        return scoreService.calculateScore(pins);
    }

}