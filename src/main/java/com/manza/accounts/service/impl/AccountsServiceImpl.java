package com.manza.accounts.service.impl;

import com.manza.accounts.dto.AccountDto;
import com.manza.accounts.model.Account;
import com.manza.accounts.repository.AccountRepository;
import com.manza.accounts.service.AccountsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountsServiceImpl implements AccountsService {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AccountRepository accountRepository;

    public AccountDto patch(AccountDto accountDto) {
        Account account = accountRepository.findByAccountId(accountDto.getId()).orElse(new Account());

        account.setAvailableCreditLimit(account.getAvailableCreditLimit().add(accountDto.getAvailableCreditLimit()));
        account.setAvailableWithdrawalLimit(account.getAvailableWithdrawalLimit().add(accountDto.getAvailableWithdrawalLimit()));

        return modelMapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }
}
