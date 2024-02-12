package com.payhere.shop.util.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EncryptionService {
    
    public final String SHA256 = "SHA-256";

    public String encryptSha256(String text) {
        String encryptText = "";

        try {
            MessageDigest md = MessageDigest.getInstance(SHA256);
            md.update(text.getBytes());
            encryptText = bytesToHex(md.digest());
        } catch(NoSuchAlgorithmException e) {
            log.error("MessageDigest Algorithm is not available", e);
        }

        return encryptText;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hexString = String.format("%02x", b);
            sb.append(hexString);
        }
        
        return sb.toString();
    }

}
