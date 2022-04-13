package com.queslime.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    private Encoder() {}

    public static String encode(String plainText){
        return bCryptPasswordEncoder.encode(plainText);
    }

    public static boolean notMatch(String plainText, String cipherText){
        return !bCryptPasswordEncoder.matches(plainText, cipherText);
    }
}