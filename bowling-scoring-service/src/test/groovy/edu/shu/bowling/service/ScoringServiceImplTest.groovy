package edu.shu.bowling.service

import edu.shu.bowling.common.ValidationException
import edu.shu.bowling.model.Bowler
import edu.shu.bowling.model.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ScoringServiceImplTest extends Specification {

    @Autowired
    private ScoreService scoreService;
    def "Start a new Game with 6 players"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())

        def bowlers = new HashSet<Bowler>();
        for(int i=0;i<6;i++) {
            def bowler = new Bowler()
            bowler.setName("Player"+i)
            bowlers.add(bowler)
        }
        game.setBowlers(bowlers);
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

        def bowlers = new HashSet<Bowler>();

            def bowler = new Bowler()
            bowler.setName("Player")
            bowlers.add(bowler)

        game.setBowlers(bowlers);
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
        def result = scoreService.startGame(game)
        then: "should throw exception"
        final ValidationException exception = thrown()
        final message = exception.message
        message == "The number of players in a game must be between 1 and 6\n"
    }
}
