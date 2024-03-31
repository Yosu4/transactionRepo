package com.bank.transaction.dao.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewAccountDetails {
    private String firstName;
    private String lastName;
    private Long accountNumber;
    private String email;
    private Long initialBalance;

    public NewAccountDetails(String firstName, String lastName, Long accountNumber, String email, Long initialBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.email = email;
        this.initialBalance = initialBalance;
    }

    @Override
    public String toString() {
        return "NewAccountDetails [firstName=" + firstName + ", lastName=" + lastName + ", accountNumber=" + accountNumber +
                ", email=" + email + ", initialBalance=" + initialBalance +"]";
    }
}
