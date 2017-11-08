package edu.shu.bowling.service;

import edu.shu.bowling.common.AccountAlreadyExistException;
import edu.shu.bowling.model.Account;
import edu.shu.bowling.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account register(Account account) {

            if(accountRepository.exists(account.getUserId()))
            {
                throw new AccountAlreadyExistException("Account Already Exist");
            }
            else if (accountRepository.findByEmailAddress(account.getEmail()) !=null)
            {
                throw new AccountAlreadyExistException("Account Already Exist");
            }
            return accountRepository.saveAndFlush(account);
    }
}
