package edu.shu.bowling.service;

import edu.shu.bowling.model.Account;
import edu.shu.bowling.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean register(Account account) {
        try {
            accountRepository.saveAndFlush(account);
        }catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
