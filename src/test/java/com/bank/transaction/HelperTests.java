package com.bank.transaction;

import com.bank.transaction.Util.Helper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelperTests {
    @Test
    public void testUsingSimpleRegex() {
        String emailAddress = "username@domain.com";

        assertTrue(Helper.isEmailPatternMatches(emailAddress));
    }
}
