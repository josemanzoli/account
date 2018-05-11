package com.manza.accounts.controller;

import com.manza.accounts.dto.AccountDto;
import com.manza.accounts.exception.AccountNotFoundException;
import com.manza.accounts.service.AccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsController.class);

    @Autowired
    private AccountsService accountsService;

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AccountDto> patch(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        accountDto.setId(id);
        try {
            return new ResponseEntity<>(accountsService.patch(accountDto), HttpStatus.OK);
        } catch (AccountNotFoundException ae) {
            LOGGER.info(ae.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/limits", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AccountDto>> get(){
        try {
            List<AccountDto> accounts = accountsService.findAll();
            if (accounts.size() > 0) {
                return new ResponseEntity<>(accounts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(accounts, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = "/limit/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AccountDto> get(@PathVariable Long id){
        try {
            AccountDto accountDto = accountsService.findByAccountId(id);
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        } catch (AccountNotFoundException ae) {
            LOGGER.info(ae.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
