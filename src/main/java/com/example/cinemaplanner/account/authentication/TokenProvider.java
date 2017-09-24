package com.example.cinemaplanner.account.authentication;

import com.example.cinemaplanner.account.exceptions.TokenExpiredException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * Created by Andreï on 07/04/2017 for ZKY.
 */


@Component
public class TokenProvider {

    private final
    AccountService accountService;
    private ShaPasswordEncoder encoder;

    private static String TOKEN_SEPARATOR = "!!!";
    private static final String NEW_LINE = "\r\n";
    private static final int ShaStrength = 256;
    private static final String secretKey = "SpringSecretKeyESGI";

    @Autowired
    public TokenProvider(AccountService accountService) {
        encoder = new ShaPasswordEncoder(ShaStrength);
        this.accountService = accountService;
    }

    /**
     * Get token using account.
     *
     * @param account account user.
     * @return String token
     */
    String getToken(Account account) {
        return getToken(account, DateTime.now().plusYears(1).getMillis());
    }

    /**
     * Get Token by account and expirationDate In Millis
     *
     * @param account                account user
     * @param expirationDateInMillis long date
     * @return String token
     */
    private String getToken(Account account, long expirationDateInMillis) {
        StringBuilder tokenBuilder = new StringBuilder();

        byte[] token = tokenBuilder
                .append(account.getLogin())
                .append(TOKEN_SEPARATOR)
                .append(expirationDateInMillis)
                .append(TOKEN_SEPARATOR)
                .append(buildTokenKey(account, expirationDateInMillis))
                .toString().getBytes();

        return Base64.encodeBase64String(token).replace(NEW_LINE, "");
    }

    /**
     * Check Token validity.
     *
     * @param encodedToken encoded Token user
     * @return boolean
     */
    private boolean isTokenValid(String encodedToken) {
        String[] tokenComponents = decodeAndDissectToken(encodedToken);

        if (tokenComponents == null || tokenComponents.length != 3) {
            return false;
        }

        String externalUser = tokenComponents[0];
        Long externalDate = Long.parseLong(tokenComponents[1]);
        String externalKey = tokenComponents[2];

        Account account = accountService.getAccountByLogin(externalUser);
        if (account == null) {
            return false;
        }

        String expectedKey = buildTokenKey(account, externalDate);

        byte[] expectedKeyBytes = expectedKey.getBytes();
        byte[] externalKeyBytes = externalKey.getBytes();

        if (MessageDigest.isEqual(expectedKeyBytes, externalKeyBytes)) {
            if (!new DateTime(externalDate).isBeforeNow()) {
                return true;
            } else {
                throw new TokenExpiredException();
            }
        } else {
            return false;
        }
    }

    /**
     * Build token .
     *
     * @param account                account user
     * @param expirationDateInMillis login date
     * @return String token key
     */
    private String buildTokenKey(Account account, long expirationDateInMillis) {
        String key = account.getLogin() +
                TOKEN_SEPARATOR +
                account.getPassword() +
                TOKEN_SEPARATOR +
                expirationDateInMillis +
                TOKEN_SEPARATOR +
                secretKey;

        //byte[] keyBytes = key.getBytes();
        return encoder.encodePassword(key, secretKey);
    }

    /**
     * Get Account using token
     *
     * @param token Stirng token
     * @return account user
     */
    Account getAccountFromToken(String token) {
        if (!isTokenValid(token)) {
            return null;
        }

        String[] components = decodeAndDissectToken(token);
        if (components == null || components.length != 3) {
            return null;
        }
        String login = components[0];

        return accountService.getAccountByLogin(login);
    }

    /**
     * Decode token using token.
     *
     * @param encodedToken token
     * @return String Array
     */
    private String[] decodeAndDissectToken(String encodedToken) {
        if (!Base64.isBase64(encodedToken.getBytes())) {
            return null;
        }

        // Apache Commons Base64 expects encoded strings to end with a newline, add one
        if (!encodedToken.endsWith(NEW_LINE)) {
            encodedToken = encodedToken + NEW_LINE;
        }

        String token = new String(Base64.decodeBase64(encodedToken));

        if (!token.contains(TOKEN_SEPARATOR) || token.split(TOKEN_SEPARATOR).length != 3) {
            return null;
        }

        return token.split(TOKEN_SEPARATOR);
    }
}
