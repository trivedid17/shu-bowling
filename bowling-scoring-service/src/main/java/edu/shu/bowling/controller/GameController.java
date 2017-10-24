package edu.shu.bowling.controller;

import edu.shu.bowling.model.Bowler;
import edu.shu.bowling.model.Game;
import edu.shu.bowling.rest.input.CalculateScoreInput;
import edu.shu.bowling.rest.output.GameStartOutput;
import edu.shu.bowling.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Set;

@RestController
public class GameController {

    @Autowired
    ScoreService scoreService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public GameStartOutput startGame(@RequestBody Game game) {
        GameStartOutput output = new GameStartOutput();
        String result = scoreService.startGame(game);
        output.setGameId(result);
        return output;
    }

    @RequestMapping(value = "/score", method = RequestMethod.POST)
    @ResponseBody
    public Game calculateScore(@RequestBody CalculateScoreInput pins)
    {

       return scoreService.calculteScore(pins);
    }

}