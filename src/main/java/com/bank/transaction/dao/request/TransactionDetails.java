package com.bank.transaction.dao.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TransactionDetails {
    private String accno;
    private String name;
    private String acc_type;
    private long balance;
}
