package edu.shu.bowling.repository

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

        given: "a game object"
            def game = new Game()
                game.setLaneId(1)
                game.setStartTime(new Date())

        when: "game is saved"
            def result = repository.saveAndFlush(game)
        then: "gameid in result should be null"
        result.gameId != null
    }
}
