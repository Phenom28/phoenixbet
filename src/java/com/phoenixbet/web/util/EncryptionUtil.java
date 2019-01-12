package com.phoenixbet.web.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Ogundipe Segun David
 */
public class EncryptionUtil {
    
    public static String generateMD5(String value){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(value.getBytes());
            
            BigInteger number = new BigInteger(1, messageDigest);
            return  number.toString(16);
        } catch (NoSuchAlgorithmException nsae) {
            return null;
        }
    }
    
    public static String generateSHA256(String value){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(value.getBytes());
            
            BigInteger number = new BigInteger(1, messageDigest);
            return number.toString(16);
        } catch (NoSuchAlgorithmException nsae) {
            return null;
        }
    }
}
