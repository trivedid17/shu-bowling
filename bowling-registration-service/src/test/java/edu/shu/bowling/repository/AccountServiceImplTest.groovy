package edu.shu.bowling.repository

import edu.shu.bowling.model.Account
import edu.shu.bowling.repository.AccountRepository
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification


@SpringBootTest
@DataJpaTest
class AccountServiceTest extends Specification {
    @Autowired
    private AccountRepository repository

    def "Save account record in database"() {

        given: "a account"
            def account = new Account()
                account.setUserId("jignesh")
                account.setFirstName("Jignesh")
                account.setLastName("Togadiya")
                account.setBirthDate(new Date())
                account.setEmail("about@com")
                account.setPhone("46583465")
                account.setPassword("sadasdas")
        when: "account is registered"
            def result = repository.saveAndFlush(account)
        then: "record should be saved"
        result.firstName == "jignesh"
    }
}
