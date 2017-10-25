package edu.shu.bowling.repository

import edu.shu.bowling.model.Bowler
import edu.shu.bowling.model.FrameId
import edu.shu.bowling.model.Game
import edu.shu.bowling.model.GameBowler
import edu.shu.bowling.model.LastFrame
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class LastFrameRepositroyTest extends Specification {
    @Autowired
    private LastFrameRepository repository

    @Autowired
    private BowlerRepositroy bowlerRepository

    @Autowired
    private GameRepository gameRepository


    def "Frame  record in database"() {

        given: "a Frame object"
        def frame = new LastFrame()

        def game = new Game()
        game.setLaneId(2)
        game.setStartTime(new Date())

        def bowler = new Bowler()
        bowler.setName("Player")
        bowlerRepository.save(bowler)

        def gameBowler =new GameBowler()
        gameBowler.setSeqNo((byte)1)
        gameBowler.setGame(game)
        gameBowler.setBowler(bowler)

        game.getBowlers().add(gameBowler)
        game=gameRepository.save(game)

        def frameId = new FrameId()

        frameId.setGame(game)
        frameId.setBowler(bowler)

        frame.setFrameId(frameId)
        frame.setThrow1((byte)10)
        frame.setThrow2((byte)10)
        frame.setThrow3((byte)2)

        when: "frame is saved"
        def result = repository.saveAndFlush(frame)
        then: "the seqno in result should not be null"
        result.frameId != null
    }
}
