package edu.shu.bowling.repository

import edu.shu.bowling.model.Bowler
import edu.shu.bowling.model.Game
import edu.shu.bowling.model.GameBowler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class GameRepositroyTest extends Specification {
    @Autowired
    private GameRepository repository

    @Autowired
    private BowlerRepositroy bowlerRepositroy


    def "Save game record in database"() {

        given: "a game object and bowler object"
        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())

        def bowler = new Bowler()
        bowler.setName("Mayak")

        bowlerRepositroy.save(bowler)

        def gameBowler = new GameBowler()
        gameBowler.setBowler(bowler)
        gameBowler.setGame(game)
        gameBowler.setSeqNo((byte)1)

        game.getBowlers().add(gameBowler)


        when: "game is saved"
            def result = repository.saveAndFlush(game)
        then: "gameid in result should not be null"
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
        message == "The number of players in a game must be between 1 and 6"
    }

    def "Game should not have more than 6 players"() {

        given: "game is saved"

        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())

        for(int i=0;i<7;i++) {
           def bowler = new Bowler()
            bowler.setName("Mayak"+i)
            bowlerRepositroy.save(bowler)

            def gameBowler = new GameBowler()
            gameBowler.setBowler(bowler)
            gameBowler.setGame(game)
            gameBowler.setSeqNo((byte)(i+1))

            game.getBowlers().add(gameBowler)
        }


        when: "game is saved"
        def result = repository.saveAndFlush(game)
        then: "should throw exception"
        final javax.validation.ConstraintViolationException exception = thrown()
        final message = exception.getConstraintViolations().getAt(0).message
        message == "The number of players in a game must be between 1 and 6"
    }
}
