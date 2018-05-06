package com.manza.accounts.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "available_credit_limit")
    private BigDecimal availableCreditLimit;

    @Column(name = "available_withdrawal_limit")
    private BigDecimal availableWithdrawalLimit;

    public Account(){

    }

    public Account(BigDecimal availableCreditLimit, BigDecimal availableWithdrawalLimit) {
        this.availableCreditLimit = availableCreditLimit;
        this.availableWithdrawalLimit = availableWithdrawalLimit;
    }

    public Account(Long accountId, BigDecimal availableCreditLimit, BigDecimal availableWithdrawalLimit) {
        this.accountId = accountId;
        this.availableCreditLimit = availableCreditLimit;
        this.availableWithdrawalLimit = availableWithdrawalLimit;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", availableCreditLimit=" + availableCreditLimit +
                ", availableWithdrawalLimit=" + availableWithdrawalLimit +
                '}';
    }
}
