package com.elesson.pioneer.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The {@code Security} class is util class serves to encode of user's passwords
 * before storing to database
 */
public class Security {
    private static final Logger logger = LogManager.getLogger(Security.class);

    /**
     * Encrypt the string using SHA-256 algorithm.
     *
     * @param str the password as plain text
     * @return the encoded password
     */
    public static String encrypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException ignore) {
            logger.warn("Password not encrypted");
            return str;
        }
    }
}
