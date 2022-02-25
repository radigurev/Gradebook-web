package com.example.onlinegradebook.model.entity;

import com.example.onlinegradebook.model.entity.enums.AccountType;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles extends BaseEntityLong {
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
