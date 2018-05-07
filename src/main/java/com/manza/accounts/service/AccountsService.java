package com.manza.accounts.service;

import com.manza.accounts.exception.AccountNotFoundException;
import com.manza.accounts.dto.AccountDto;

import java.util.List;

public interface AccountsService {

    AccountDto patch(AccountDto accountDto) throws AccountNotFoundException;
    List<AccountDto> findAll() throws Exception;
}
