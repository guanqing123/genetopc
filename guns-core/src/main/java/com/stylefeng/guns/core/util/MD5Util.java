package com.stylefeng.guns.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.DigestUtils;

/**
 * MD5加密类（封装jdk自带的md5加密方法）
 *
 * @author fengshuonan
 * @date 2016年12月2日 下午4:14:22
 */
public class MD5Util {
	
	public static String encrypt16(String source) {
		return encodeMd5(source.getBytes()).substring(8,24);
    }

    public static String encrypt(String source) {
        return encodeMd5(source.getBytes());
    }

    private static String encodeMd5(byte[] source) {
        try {
            return encodeHex(MessageDigest.getInstance("MD5").digest(source));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static String encodeHex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10)
                buffer.append("0");
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
    }
    
    public static String springMd5(String source) {
    	return DigestUtils.md5DigestAsHex(source.getBytes());
    }
}
