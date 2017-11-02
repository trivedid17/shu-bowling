package edu.shu.bowling.score.repository

import edu.shu.bowling.score.model.Game
import edu.shu.bowling.score.model.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import javax.validation.ConstraintViolationException

@DataJpaTest
class GameRepositoryTest extends Specification {
    @Autowired
    private GameRepository repository


    def "Save game record in database"() {

        given: "a game object and bowler object"
            def game = new Game()
            game.setLaneId(1)
            game.setStartTime(new Date())

            def player = new Player()
            player.setPlayerName("Player")
            player.setSeqNo(1)
            game.getPlayers().add(player)

        when: "game is saved"
            def result = repository.saveAndFlush(game)
        then: "game id in result should not be null"
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
            final ConstraintViolationException exception = thrown()
            final message = exception.getConstraintViolations()[0].message
            message == "The number of players in a game must be between 1 and 6"
    }

    def "Game should not have more than 6 players"() {

        given: "game is saved"
            def game = new Game()
            game.setLaneId(1)
            game.setStartTime(new Date())

            for (int i = 0; i < 7; i++) {
                def player = new Player()
                player.setPlayerName("Player" + i)
                player.setSeqNo(i + 1)
                game.getPlayers().add(player)
            }

        when: "game is saved"
            def result = repository.saveAndFlush(game)
        then: "should throw exception"
            final ConstraintViolationException exception = thrown()
            final message = exception.getConstraintViolations()[0].message
            message == "The number of players in a game must be between 1 and 6"
    }
}
