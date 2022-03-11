package com.queslime.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private Encoder() {}

    public static String encode(String plainText){
        return bCryptPasswordEncoder.encode(plainText);
    }

    public static boolean match(String plainText, String cipherText){
        return bCryptPasswordEncoder.matches(plainText, cipherText);
    }
}