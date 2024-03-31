package com.bank.transaction.service;

import com.bank.transaction.Util.Helper;
import com.bank.transaction.dao.request.NewAccountDetails;
import com.bank.transaction.dao.request.TransferDetails;
import com.bank.transaction.dao.response.ResponseEntity;
import com.bank.transaction.model.AccountDetails;
import com.bank.transaction.model.TransactionHistory;
import com.bank.transaction.repository.AccountRepository;
import com.bank.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.bank.transaction.Util.CommonUtil.*;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public ResponseEntity getAccounts(){
        return ResponseEntity.builder()
                .status(SUCCESS)
                .statusCode(String.valueOf(HttpStatus.OK))
                .bodyResponse(accountRepository.findAll())
                .build();
    }

    public ResponseEntity createAccount(NewAccountDetails newAccountDetails){
        if(!Helper.isEmailPatternMatches(newAccountDetails.getEmail())){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.BAD_REQUEST))
                    .bodyResponse(ERROR_EMAIL_VALIDATION)
                    .build();
        }

        if(!Helper.isAccountNumberPatternMatches(newAccountDetails.getAccountNumber())){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.BAD_REQUEST))
                    .bodyResponse(ERROR_ACCOUNT_NUMBER_VALIDATION)
                    .build();
        }

        if(newAccountDetails.getFirstName().isBlank() || newAccountDetails.getLastName().isBlank()){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.BAD_REQUEST))
                    .bodyResponse("Name should not be empty")
                    .build();
        }

        if(newAccountDetails.getInitialBalance() < 0){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.BAD_REQUEST))
                    .bodyResponse("Balance can not less than 0")
                    .build();
        }

        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setBalance(newAccountDetails.getInitialBalance());
        accountDetails.setBankAccountNumber(newAccountDetails.getAccountNumber());
        accountDetails.setEmail(newAccountDetails.getEmail());
        accountDetails.setFirstName(newAccountDetails.getFirstName());
        accountDetails.setLastName(newAccountDetails.getLastName());

        accountRepository.save(accountDetails);

        return ResponseEntity.builder()
                .status(SUCCESS)
                .statusCode(String.valueOf(HttpStatus.CREATED))
                .bodyResponse("1")
                .build();
    }

    public ResponseEntity depositAccount(Long accountNumber, Long amount) {
        if(!Helper.isAccountNumberPatternMatches(accountNumber)){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.BAD_REQUEST))
                    .bodyResponse(ERROR_ACCOUNT_NUMBER_VALIDATION)
                    .build();
        }

        List<AccountDetails> listAccountDetails = accountRepository.findByBankAccountNumber(accountNumber);
        if(listAccountDetails.size() == 0){
            return ResponseEntity.builder()
                    .statusCode(String.valueOf(HttpStatus.NOT_FOUND))
                    .status(FAILED)
                    .bodyResponse(ERROR_ACCOUNT_NUMBER_NOT_FOUND)
                    .build();
        } else {
            AccountDetails accountDetails = listAccountDetails.get(0);
            accountDetails.setBalance(accountDetails.getBalance()+amount);
            accountRepository.save(accountDetails);

            TransactionHistory transactionHistory = TransactionHistory.builder()
                    .accountNumber(accountDetails.getBankAccountNumber())
                    .remainingBalance(accountDetails.getBalance())
                    .deposit(amount)
                    .createdDate(new Date())
                    .transactionType(DEPOSIT)
                    .build();
            transactionRepository.save(transactionHistory);

            return ResponseEntity.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .statusCode("200")
                    .bodyResponse("1")
                    .build();
        }
    }

    public ResponseEntity getSingleAccount(Long accountNumber){
        if(!Helper.isAccountNumberPatternMatches(accountNumber)){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.BAD_REQUEST))
                    .bodyResponse(ERROR_ACCOUNT_NUMBER_VALIDATION)
                    .build();
        }

        List<AccountDetails> accountDetails = accountRepository.findByBankAccountNumber(accountNumber);
        if(accountDetails.size() == 0){
            return ResponseEntity.builder()
                    .statusCode(String.valueOf(HttpStatus.NOT_FOUND))
                    .status("Failed")
                    .bodyResponse(ERROR_ACCOUNT_NUMBER_NOT_FOUND)
                    .build();
        } else {
            return ResponseEntity.builder()
                    .statusCode(String.valueOf(HttpStatus.OK))
                    .status("Success")
                    .bodyResponse(accountDetails.get(0))
                    .build();
        }
    }

    public ResponseEntity sendMoney(TransferDetails transferDetails) {
        List<AccountDetails> destinationAccount =
                accountRepository.findByBankAccountNumber(transferDetails.getDestinationAccountNumber());
        if(destinationAccount.size() == 0){
            return ResponseEntity.builder()
                    .statusCode(String.valueOf(HttpStatus.NOT_FOUND))
                    .status("Failed")
                    .bodyResponse("Destination Account Number is not found")
                    .build();
        }

        List<AccountDetails> sourceAccount =
                accountRepository.findByBankAccountNumber(transferDetails.getSourceAccountNumber());
        if(sourceAccount.size() == 0){
            return ResponseEntity.builder()
                    .statusCode(String.valueOf(HttpStatus.NOT_FOUND))
                    .status("Failed")
                    .bodyResponse("Source Account Number is not found")
                    .build();
        }

        if(sourceAccount.get(0).getBalance() < transferDetails.getAmount()){
            return ResponseEntity.builder()
                    .statusCode(String.valueOf(HttpStatus.EXPECTATION_FAILED))
                    .status("Failed")
                    .bodyResponse("Not enough balance")
                    .build();
        } else {
            AccountDetails updateSourceAcc = sourceAccount.get(0);
            updateSourceAcc.setBalance(updateSourceAcc.getBalance()-transferDetails.getAmount());
            accountRepository.save(updateSourceAcc);

            AccountDetails updateDestinationAcc = destinationAccount.get(0);
            updateDestinationAcc.setBalance(updateDestinationAcc.getBalance()+transferDetails.getAmount());
            accountRepository.save(updateDestinationAcc);

            TransactionHistory transactionHistory = TransactionHistory.builder()
                    .accountNumber(updateSourceAcc.getBankAccountNumber())
                    .destinationAccountNumber(updateDestinationAcc.getBankAccountNumber())
                    .remainingBalance(updateSourceAcc.getBalance())
                    .transfer(transferDetails.getAmount())
                    .createdDate(new Date())
                    .comment(transferDetails.getMessage())
                    .transactionType(TRANSFER)
                    .build();
            transactionRepository.save(transactionHistory);

            return ResponseEntity.builder()
                    .statusCode(String.valueOf(HttpStatus.OK))
                    .status("Success")
                    .bodyResponse("Your Balance now is : " + updateSourceAcc.getBalance())
                    .build();
        }
    }
}
