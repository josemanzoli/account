package com.manza.accounts.service.impl;

import com.manza.accounts.exception.AccountNotFoundException;
import com.manza.accounts.dto.AccountDto;
import com.manza.accounts.model.Account;
import com.manza.accounts.repository.AccountRepository;
import com.manza.accounts.service.AccountsService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountsServiceImpl implements AccountsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AccountRepository accountRepository;

    public AccountDto patch(AccountDto accountDto) throws AccountNotFoundException {
        Account account = accountRepository.findByAccountId(accountDto.getId())
                .orElseThrow(() -> new AccountNotFoundException("Account" + accountDto.getId().toString() + " was not found" ));

        account.setAvailableCreditLimit(account.getAvailableCreditLimit().add(accountDto.getAvailableCreditLimit()));
        account.setAvailableWithdrawalLimit(account.getAvailableWithdrawalLimit().add(accountDto.getAvailableWithdrawalLimit()));

        return modelMapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Override
    public List<AccountDto> findAll() throws Exception {
        try {
             return accountRepository.findAll()
                    .stream()
                    .map(account -> {
                        LOGGER.debug("Converting account: {} to AccountDto", account);
                        return modelMapper.map(account, AccountDto.class);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Could not map Account to AccountDto. Exception : {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountDto findByAccountId(Long id) throws AccountNotFoundException {
        Account account = accountRepository.findByAccountId(id)
                .orElseThrow(() -> new AccountNotFoundException("Account" + id.toString() + " was not found" ));

        return modelMapper.map(account, AccountDto.class);
    }
}
