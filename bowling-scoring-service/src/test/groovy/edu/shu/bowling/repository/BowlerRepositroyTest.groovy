package edu.shu.bowling.repository

import edu.shu.bowling.model.Bowler
import edu.shu.bowling.model.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class BowlerRepositroyTest extends Specification {
    @Autowired
    private BowlerRepositroy repository


    def "Save bowler record in database"() {

        given: "a bowler object"
            def bowler = new Bowler()
            bowler.setName("Jignesh")

        when: "bowler is saved"
            def result = repository.saveAndFlush(bowler)
        then: "bowlerid in result should be null"
        result.bowlerId != null
    }
}
