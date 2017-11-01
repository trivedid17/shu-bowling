package edu.shu.bowling.repository

import edu.shu.bowling.model.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class AccountRepositoryTest extends Specification {
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
        account.setPhone("2035554444")
        account.setPassword("@df#Asder123")
        when: "account is saved"
        def result = repository.saveAndFlush(account)
        then: "record should be saved"
        result.firstName == "Jignesh"
    }
}
