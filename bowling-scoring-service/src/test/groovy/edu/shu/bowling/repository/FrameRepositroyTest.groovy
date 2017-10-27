package edu.shu.bowling.repository

import edu.shu.bowling.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class FrameRepositroyTest extends Specification {
    @Autowired
    private FrameRepository repository

    @Autowired
    private GameRepository gameRepository

    @Autowired
    private BowlerRepository bowlerRepository


    def "Frame  record in database"() {

        given: "a Frame object"
        def frame = new Frame()

        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())

        def bowler = new Bowler()
        bowler.setName("Player")
        bowlerRepository.save(bowler)

        def gameBowler = new GameBowler()
        gameBowler.setSeqNo((byte) 1)
        gameBowler.setGame(game)
        gameBowler.setBowler(bowler)

        game.getBowlers().add(gameBowler)
        game = gameRepository.save(game)

        def frameId = new FrameId()

        frameId.setGame(game)
        frameId.setBowler(bowler)

        frame.setFrameId(frameId)
        frame.setThrow1((byte) 3)
        frame.setThrow2((byte) 2)

        when: "frame is saved"
        def result = repository.saveAndFlush(frame)
        then: "the seqno in result should not be null"
        result.frameId != null
    }
}
