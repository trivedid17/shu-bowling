package edu.shu.bowling.repository

import edu.shu.bowling.model.Bowler
import edu.shu.bowling.repository.GameRepository
import edu.shu.bowling.model.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class GameRepositroyTest extends Specification {
    @Autowired
    private GameRepository repository


    def "Save game record in database"() {

        given: "a game object and bowler object"
        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())

        def bowler = new Bowler()
        bowler.setName("Mayak")

        def bowlers =new HashSet<Bowler>()
        bowlers.add(bowler)

        game.setBowlers(bowlers)

        when: "game is saved"
            def result = repository.saveAndFlush(game)
        then: "gameid in result should be null"
        result.gameId != null
    }

    def "Game should have at least one player"() {

        given: "a game object"

        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())

        when: "game is saved"
        def result = repository.saveAndFlush(game)
        then: "should throw exception"
        final javax.validation.ConstraintViolationException exception = thrown()
        final message = exception.getConstraintViolations().getAt(0).message
        message == "The number of players in a game must be between 1 and 7"
    }

    def "Game should not have more than 7 players"() {

        given: "game is saved"

        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())

        def bowlers =new HashSet<Bowler>()

        for(int i=0;i<8;i++) {
           def bowler = new Bowler()
            bowler.setName("Mayak"+i)
            bowlers.add(bowler)
        }
        game.setBowlers(bowlers)

        when: "game is saved"
        def result = repository.saveAndFlush(game)
        then: "should throw exception"
        final javax.validation.ConstraintViolationException exception = thrown()
        final message = exception.getConstraintViolations().getAt(0).message
        message == "The number of players in a game must be between 1 and 7"
    }
}
