package edu.shu.bowling.service

import edu.shu.bowling.common.GameNotActiveException
import edu.shu.bowling.common.GameNotExistException
import edu.shu.bowling.common.ValidationException
import edu.shu.bowling.model.Bowler
import edu.shu.bowling.model.Game
import edu.shu.bowling.model.GameBowler
import edu.shu.bowling.model.GameStatus
import edu.shu.bowling.repository.BowlerRepository
import edu.shu.bowling.repository.GameRepository
import edu.shu.bowling.rest.input.CalculateScoreInput
import edu.shu.bowling.rest.input.StartGameInput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ScoringServiceImplTest extends Specification {

    @Autowired
    private ScoreService scoreService

    @Autowired
    private GameRepository gameRepository

    @Autowired
    private BowlerRepository bowlerRepositroy

    def "Start a new Game with 6 players"() {

        given: "A Game"
        def game = new StartGameInput()
        game.setLaneId(1)

        for (int i = 0; i < 6; i++) {
            def bowler = new Bowler()
            bowler.setName("Player" + i)
            game.getBowlers().add(bowler)
        }

        when: "game is started"
        def result = scoreService.startGame(game)
        then: "should return new gameId"
        result != "" && result != null
    }

    def "Start a new Game with 1 players"() {

        given: "A Game"
        def game = new StartGameInput()
        game.setLaneId(1)

        def bowler = new Bowler()
        bowler.setName("Mayak")
        game.getBowlers().add(bowler)

        when: "game is started"
        def result = scoreService.startGame(game)
        then: "should return new gameId"
        result != "" && result != null
    }

    def "Start a new Game without players"() {

        given: "A Game"
        def game = new StartGameInput()
        game.setLaneId(1)

        when: "game is started"
        scoreService.startGame(game)
        then: "should throw exception"
        final ValidationException exception = thrown()
        final message = exception.message
        message == "The number of players in a game must be between 1 and 6\n"
    }


    def "Play throw for a game which does not exist"() {

        given: "A pins"
        def input = new CalculateScoreInput()
        input.gameId = "Not exist"
        input.setPins((byte) 22)


        when: "game is started"
        scoreService.calculateScore(input)
        then: "should throw exception"
        thrown(GameNotExistException)
    }

    def "Play throw for a game which is not active"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())
        game.setStatus(GameStatus.FINISHED)

        def bowler = new Bowler()
        bowler.setName("Mayak")

        bowlerRepositroy.save(bowler)

        def gameBowler = new GameBowler()
        gameBowler.setBowler(bowler)
        gameBowler.setGame(game)
        gameBowler.setSeqNo((byte) 1)

        game.getBowlers().add(gameBowler)

        def result = gameRepository.saveAndFlush(game)
        def input = new CalculateScoreInput()
        input.gameId = result.getGameId()
        input.setPins((byte) 22)

        when: "game is started"
        scoreService.calculateScore(input)
        then: "should throw exception"
        thrown(GameNotActiveException)
    }
}
