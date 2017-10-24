package edu.shu.bowling.repository

import edu.shu.bowling.model.Bowler
import edu.shu.bowling.model.Frame
import edu.shu.bowling.model.FrameId
import edu.shu.bowling.model.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class FrameRepositroyTest extends Specification {
    @Autowired
    private FrameRepository repository

    @Autowired
    private GameRepository gameRepository


    def "Frame  record in database"() {

        given: "a Frame object"
           def frame = new Frame()

            def game = new Game()
            game.setLaneId(1)
            game.setStartTime(new Date())

            def bowler = new Bowler()
            bowler.setName("Player")

            def bowlers =new HashSet<Bowler>()
            bowlers.add(bowler)

            game.setBowlers(bowlers)
            game=gameRepository.saveAndFlush(game)

            def frameId = new FrameId()

            frameId.setGame(game)
            frameId.setBowler(game.getBowlers().getAt(0))

            frame.setFrameId(frameId)
            frame.setThrow1((byte)3)
            frame.setThrow2((byte)2)

        when: "frame is saved"
            def result = repository.saveAndFlush(frame)
        then: "the seqno in result should not be null"
            result.frameId != null
    }
}
