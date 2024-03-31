package com.bank.transaction.controller;

import com.bank.transaction.dao.request.NewAccountDetails;
import com.bank.transaction.dao.request.TransferDetails;
import com.bank.transaction.dao.response.ResponseEntity;
import com.bank.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.bank.transaction.Util.CommonUtil.FAILED;

@Slf4j
@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/getaccounts")
    @ResponseBody
    private ResponseEntity getAccounts(){
        log.info("Get all Account "+TransactionService.class.getName());
        try{
            return transactionService.getAccounts();
        } catch (Exception e){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }

    @PostMapping("/createaccount")
    private ResponseEntity createAccount(@RequestBody NewAccountDetails newAccountDetails){
        try{
            return transactionService.createAccount(newAccountDetails);
        } catch (Exception e){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }

    @PutMapping("/deposit/{accountnumber}")
    private ResponseEntity depositAmount(@PathVariable("accountnumber") Long accountNumber, @RequestBody Long amount){
        try{
            return transactionService.depositAccount(accountNumber, amount);
        } catch (Exception e){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }

    @PutMapping("/account/{accountnumber}")
    private ResponseEntity getAccount(@PathVariable("accountnumber") Long accountNumber){
        try{
            return transactionService.getSingleAccount(accountNumber);
        } catch (Exception e){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }

    @PostMapping("/transfer")
    private ResponseEntity sendMoney(@RequestBody TransferDetails transferDetails){
        try{
            return transactionService.sendMoney(transferDetails);
        } catch (Exception e){
            return ResponseEntity.builder()
                    .status(FAILED)
                    .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }
}
