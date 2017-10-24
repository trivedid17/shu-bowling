package edu.shu.bowling.service

import edu.shu.bowling.common.GameNotActiveException
import edu.shu.bowling.common.GameNotExistException
import edu.shu.bowling.common.ValidationException
import edu.shu.bowling.model.Bowler
import edu.shu.bowling.model.Game
import edu.shu.bowling.model.GameStatus
import edu.shu.bowling.rest.input.CalculateScoreInput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ScoringServiceImplTest extends Specification {

    @Autowired
    private ScoreService scoreService
    def "Start a new Game with 6 players"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())

        def bowlers = new HashSet<Bowler>()
        for(int i=0;i<6;i++) {
            def bowler = new Bowler()
            bowler.setName("Player"+i)
            bowlers.add(bowler)
        }
        game.setBowlers(bowlers)
        when: "game is started"
        def result = scoreService.startGame(game)
        then: "should return new gameId"
        result != "" && result != null
    }

    def "Start a new Game with 1 players"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(2)
        game.setStartTime(new Date())

        def bowlers = new HashSet<Bowler>()

            def bowler = new Bowler()
            bowler.setName("Player")
            bowlers.add(bowler)

        game.setBowlers(bowlers)
        when: "game is started"
        def result = scoreService.startGame(game)
        then: "should return new gameId"
        result != "" && result != null
    }

    def "Start a new Game without players"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(2)
        game.setStartTime(new Date())

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
        input.setPins((byte)22)

        when: "game is started"
         scoreService.calculteScore(input)
        then: "should throw exception"
        thrown(GameNotExistException)
    }

    def "Play throw for a game which is not active"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(2)
        game.setStartTime(new Date())
        game.setStatus(GameStatus.FINISHED)

        def bowlers = new HashSet<Bowler>()

        def bowler = new Bowler()
        bowler.setName("Player")
        bowlers.add(bowler)

        game.setBowlers(bowlers)

        def gameId = scoreService.startGame(game)

        def input = new CalculateScoreInput()
        input.gameId = gameId
        input.setPins((byte)22)

        when: "game is started"
         scoreService.calculteScore(input)
        then: "should throw exception"
        thrown(GameNotActiveException)
    }
}
