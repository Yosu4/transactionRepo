package com.bank.transaction.Util;

import java.util.regex.Pattern;

public class Helper {
    public static boolean isEmailPatternMatches(String emailAddress) {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(emailAddress)
                .matches();
    }

    public static boolean isAccountNumberPatternMatches(Long accountNumber) {
        return Pattern.compile("^[0-9]{1,6}$")
                .matcher(String.valueOf(accountNumber))
                .matches();
    }
}
