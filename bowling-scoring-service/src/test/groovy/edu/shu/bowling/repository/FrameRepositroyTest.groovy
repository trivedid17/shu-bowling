package edu.shu.bowling.repository

import edu.shu.bowling.model.Bowler
import edu.shu.bowling.model.Frame
import edu.shu.bowling.model.FrameId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class FrameRepositroyTest extends Specification {
    @Autowired
    private FrameRepository repository


    def "Frame  record in database"() {

        given: "a Frame object"
           def frame = new Frame()

            def frameId = new FrameId()
            frameId.setBowlerId("123")
            frameId.setGameId("34")
            frameId.setSeqNo((byte)5)

            frame.setFrameId(frameId)
            frame.setThrow1((byte)3)
            frame.setThrow2((byte)2)

        when: "frame is saved"
            def result = repository.saveAndFlush(frame)
        then: "the seqno in result should not be null"
            result.frameId != null
    }
}
