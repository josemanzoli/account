package com.manza.accounts.service;

import com.manza.accounts.application.Application;
import com.manza.accounts.dto.AccountDto;
import com.manza.accounts.model.Account;
import com.manza.accounts.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountsServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountsService accountsService;

    @Test
    public void shouldReturnAccountList() throws Exception {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1L,new BigDecimal(1000), new BigDecimal(1000)));
        accounts.add(new Account(2L,new BigDecimal(1000), new BigDecimal(1000)));

        when(accountRepository.findAll()).thenReturn(accounts);

        List<AccountDto> accountDtos = accountsService.findAll();

        assertThat(accountDtos.size()).isEqualTo(2);

        assertThat(accountDtos.get(0).getId()).isEqualTo(accounts.get(0).getAccountId());
        assertThat(accountDtos.get(0).getAvailableCreditLimit()).isEqualTo(accounts.get(0).getAvailableCreditLimit());
        assertThat(accountDtos.get(0).getAvailableWithdrawalLimit()).isEqualTo(accounts.get(0).getAvailableWithdrawalLimit());

        assertThat(accountDtos.get(1).getId()).isEqualTo(accounts.get(1).getAccountId());
        assertThat(accountDtos.get(1).getAvailableCreditLimit()).isEqualTo(accounts.get(1).getAvailableCreditLimit());
        assertThat(accountDtos.get(1).getAvailableCreditLimit()).isEqualTo(accounts.get(1).getAvailableCreditLimit());
    }

    @Test
    public void shouldReturnEmptyList() throws Exception {
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());

        List<AccountDto> accountDtos = accountsService.findAll();

        assertThat(accountDtos.size()).isEqualTo(0);
    }

    @Test(expected = Exception.class)
    public void shouldThrowException() throws Exception {
        when(accountRepository.findAll()).thenReturn(null);

        accountsService.findAll();
    }
}
