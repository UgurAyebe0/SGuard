package com.softepen.Mysql;

import java.util.Random;

public class generateVerificationKey {
    public static String generateVerificationKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder keyBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(characters.length());
            keyBuilder.append(characters.charAt(index));
        }
        return keyBuilder.toString();
    }
}
