package com.bank.transaction.dao.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TransferDetails {
    private Long destinationAccountNumber;
    private Long sourceAccountNumber;
    private Long amount;
    private String message;
}
