package com.deepak.mlm.util;

import com.deepak.mlm.enums.Level;

import java.security.SecureRandom;
import java.util.Random;

/**
 * This is our util class, which has some util method.
 * @author Sudo-Deepak
 */
public class CommonUtil {
    public static String generateReferralCode() {
        char[] alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] numbers = "1234567890".toCharArray();
        return getRandomString(alphabets, 3, true)
                + "-"
                + getRandomString(numbers, 3, false)
                + "-"
                + getRandomString(alphabets, 3, true);
    }

    public static String getRandomString(char[] arr, int length, boolean convertToUpperCase) {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char alphabet = arr[random.nextInt(arr.length)];
            if (convertToUpperCase && i == 0) {
                alphabet = Character.toUpperCase(alphabet);
            }
            sb.append(alphabet);
        }
        return sb.toString();
    }

    public static int calculateAndGetCommission(Level level) {
        int value = 0;
        if(level == Level.L3) {
            value = 10;
        } else if(level == Level.L2) {
            value = 5;
        }
        return value;
    }
}
