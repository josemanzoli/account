package com.manza.accounts.controller;

import com.manza.accounts.dto.AccountDto;
import com.manza.accounts.service.AccountsService;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<AccountDto>> get(){
        try {
            List<AccountDto> accounts = accountsService.findAll();
            if (accounts.size() > 0) {
                return new ResponseEntity<>(accounts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(accounts, HttpStatus.NO_CONTENT);
            }
        } catch (MappingException me) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
