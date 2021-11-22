package com.B305.ogym.common.encrypt_sha256;

import java.security.MessageDigest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EncryptSha256 {
    // SHA-256으로 encrypt 하는 메서드
    public static String encrypt(String s){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = s.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for(byte d : digested){
                sb.append(Integer.toString((d & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch(Exception e){
            e.printStackTrace();
            return s;
        }
    }
}
