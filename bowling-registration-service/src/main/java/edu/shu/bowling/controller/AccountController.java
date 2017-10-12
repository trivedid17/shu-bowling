package edu.shu.bowling.controller;

import edu.shu.bowling.model.Account;
import edu.shu.bowling.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Account registerBowler(@RequestBody Account account) {
        return accountService.register(account);
    }

}