package com.manza.accounts.controller;

import com.manza.accounts.application.Application;
import com.manza.accounts.dto.AccountDto;
import com.manza.accounts.model.Account;
import com.manza.accounts.service.AccountsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class AccountsControllerTest {

    private final String result = "[{\"id\":1,\"available_credit_limit\":1000,\"available_withdrawal_limit\":1000},{\"id\":2,\"available_credit_limit\":2000,\"available_withdrawal_limit\":2000}]";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountsService accountsService;

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void shouldReturnAccountList() throws Exception {
        when(accountsService.findAll()).thenReturn(new ArrayList<>(
                Stream.of(
                        new Account(1L, new BigDecimal(1000), new BigDecimal(1000)),
                        new Account(2L, new BigDecimal(2000), new BigDecimal(2000))
                )
                        .map(account -> modelMapper.map(account, AccountDto.class))
                        .collect(Collectors.toList())
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/limits").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(result)));
    }
}
