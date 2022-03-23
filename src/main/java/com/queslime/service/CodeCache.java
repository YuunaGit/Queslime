package com.queslime.service;

import org.springframework.stereotype.Service;

import java.util.Hashtable;

@Service
public class CodeCache {
    private final Hashtable<Integer, String> cache = new Hashtable<>();
    // TODO GENERATE THIS FUCKING CODE!!!
    public int generateRandomCode() {
        int code = (int) (System.currentTimeMillis() >> 32);
        code ^= (int) System.currentTimeMillis();
        return code < 0 ? -code : code;
    }

    public void putCode(Integer id, String code) {
        cache.put(id, code);
    }

    public boolean validateCode(Integer id, String code) {
        String cacheCode = cache.get(id);
        if(cacheCode != null && cacheCode.equals(code)) {
            cache.remove(id);
            return true;
        }
        return false;
    }

}
