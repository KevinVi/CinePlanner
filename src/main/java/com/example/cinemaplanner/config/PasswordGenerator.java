package com.example.cinemaplanner.config;

/**
 * Created by Kevin on 10/07/2017 for ZKY.
 * Password generator
 */

import java.math.BigInteger;
import java.security.SecureRandom;

public class PasswordGenerator {
    private SecureRandom random = new SecureRandom();

    /**
     * Generate random alpha numeric string of 8 char
     * @return String
     */
    public String nextSessionId() {
        String secure = new BigInteger(130, random).toString(32);
        return secure.substring(0, Math.min(secure.length(), 8));

    }
}