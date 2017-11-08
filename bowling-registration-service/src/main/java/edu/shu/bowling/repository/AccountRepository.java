package edu.shu.bowling.repository;

import edu.shu.bowling.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("select a from Account a where a.email = ?1")
    Account findByEmailAddress(String email);

}
