package edu.shu.bowling.controller;

import edu.shu.bowling.model.Account;
import edu.shu.bowling.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AccountController {

	@Autowired
	AccountService accountService;

	@RequestMapping(value="/register", method= RequestMethod.GET)
	public Account registerBowler()
	{
		Account account = new Account();
		account.setUserId("jignesh");
		account.setFirstName("Jignesh");
		account.setLastName("Togadiya");
		account.setBirthDate(new Date());
		account.setEmail("about@com");
		account.setPhone("46583465");
		account.setPassword("sadasdas");
		accountService.register(account);
		return account;

	}

}