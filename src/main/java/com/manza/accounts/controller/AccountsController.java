package com.manza.accounts.controller;

import com.manza.accounts.dto.AccountDto;
import com.manza.accounts.model.Account;
import com.manza.accounts.service.AccountsService;
import com.manza.accounts.service.impl.AccountsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AccountDto patch(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        accountDto.setId(id);
        return accountsService.patch(accountDto);
    }

    @GetMapping(path = "/limits", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<AccountDto> get(){
        return accountsService.findAll();
    }
}
