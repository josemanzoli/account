package com.manza.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public class AccountDto {
    private Long id;
    @JsonProperty
    private BigDecimal availableCreditLimit;
    @JsonProperty
    private BigDecimal availableWithdrawalLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAvailableCreditLimit() {
        return availableCreditLimit;
    }

    public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
        this.availableCreditLimit = availableCreditLimit;
    }

    public BigDecimal getAvailableWithdrawalLimit() {
        return availableWithdrawalLimit;
    }

    public void setAvailableWithdrawalLimit(BigDecimal availableWithdrawalLimit) {
        this.availableWithdrawalLimit = availableWithdrawalLimit;
    }
}
