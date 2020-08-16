package com.cuiwq.common.util;

import org.apache.commons.lang.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author cuiwq
 * @date 2018-12-19 星期三
 */
public class DigestUtil {
    
    private static final String HASH_TYPE_MD5 = "MD5";
    
    private static final String HASH_TYPE_SHA1 = "SHA1";
    
    public static String md5(String content) {
        return getHash(content, HASH_TYPE_MD5);
    }
    
    public static String md5(String content, String key) {
        return getHash(content, key, HASH_TYPE_MD5);
    }
    
    public static String sha1(String content) {
        return getHash(content, HASH_TYPE_SHA1);
    }
    
    public static String sha1(String content, String key) {
        return getHash(content, key, HASH_TYPE_SHA1);
    }
    
    public static String getHash(String content, String hashType) {
        return getHash(content, null, hashType);
    }
    
    public static String getHash(String content, String key, String hashType) {
        return getHash(content, key, hashType, StandardCharsets.UTF_8);
    }
    
    public static String getHash(String content, String key, String hashType, Charset charset) {
        byte[] byteContent = content.getBytes(charset);
        if(StringUtils.isNotEmpty(key)) {
            byte[] keyByte = key.getBytes(charset);
            int contentLength = byteContent.length;
            int keyLength = keyByte.length;
            byteContent = Arrays.copyOf(byteContent, contentLength + keyLength);
            System.arraycopy(keyByte, 0, byteContent, contentLength, keyLength);
        }
        return getHash(byteContent, hashType);
    }
    
    public static String getHash(byte[] byteContent, String hashType) {
        try {
            MessageDigest md = MessageDigest.getInstance(hashType);
            byte[] array = md.digest(byteContent);
            StringBuilder sb = new StringBuilder();
            for(byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch(NoSuchAlgorithmException e) {
            //error action
        }
        return null;
    }
    
}