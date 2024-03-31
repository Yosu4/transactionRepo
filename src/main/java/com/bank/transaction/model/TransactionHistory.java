package com.bank.transaction.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "destination_account_number")
    private Long destinationAccountNumber;

    @Column(name = "deposit")
    private Long deposit;

    @Column(name = "withdrawl")
    private Long withdrawal;

    @Column(name = "transfer")
    private Long transfer;

    @Column(name = "remaining_balance")
    private Long remainingBalance;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_date")
    private Date createdDate;

}
