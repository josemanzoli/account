package com.manza.accounts.controller;

import com.manza.accounts.exception.AccountNotFoundException;
import com.manza.accounts.application.Application;
import com.manza.accounts.dto.AccountDto;
import com.manza.accounts.model.Account;
import com.manza.accounts.service.AccountsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    private final String content = "{\"available_credit_limit\":150,\"available_withdrawal_limit\":-150}";
    private final String limits = "/v1/accounts/limits";
    private final String patch = "/v1/accounts";
    private final String noContent = "[]";
    private ModelMapper modelMapper = new ModelMapper();
    private AccountDto accountDto = new AccountDto();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountsService accountsService;

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

        mockMvc.perform(MockMvcRequestBuilders.get(limits).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(result)));
    }

    @Test
    public void shouldReturnEmptyAccountList() throws Exception {
        when(accountsService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get(limits).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNoContent())
                .andExpect(content().string(equalTo(noContent)));
    }

    @Test
    public void shouldThrowException () throws Exception {
        when(accountsService.findAll()).thenThrow(new MappingException(new ArrayList<>()));

        mockMvc.perform(MockMvcRequestBuilders.get(limits).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldReturnPatchedObject() throws Exception {
        accountDto.setId(1L);

        when(accountsService.patch(accountDto)).thenReturn(accountDto);

        mockMvc.perform(MockMvcRequestBuilders.patch(patch+"/1")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(accountDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available_credit_limit")
                        .value(accountDto.getAvailableCreditLimit()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available_withdrawal_limit")
                        .value(accountDto.getAvailableWithdrawalLimit()))
        ;
    }

    @Test
    public void shouldThrowAccountNotFoundException () throws Exception {
        accountDto.setId(1L);

        when(accountsService.patch(accountDto)).thenThrow(new AccountNotFoundException("error"));

        mockMvc.perform(MockMvcRequestBuilders.patch(patch+"/1")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(content))
                .andExpect(status().isNotFound());
    }
}
