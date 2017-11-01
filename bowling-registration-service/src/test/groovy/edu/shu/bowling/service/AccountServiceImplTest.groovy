package edu.shu.bowling.service

import edu.shu.bowling.model.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AccountServiceImplTest extends Specification {
    @Autowired
    private AccountService accountService

    def "Register Account Service"() {

        given: "a account"
        def account = new Account()
        account.setUserId("jignesh1")
        account.setFirstName("Jignesh1")
        account.setLastName("Togadiya1")
        account.setBirthDate(new Date())
        account.setEmail("about1@com")
        account.setPhone("2035554444")
        account.setPassword("@df#Asder123")
        when: "account is registered"
        def result = accountService.register(account)
        then: "registed account should be return"
        result.firstName == "Jignesh1"
    }
}
