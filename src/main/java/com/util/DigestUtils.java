package com.util;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * Created by 63289 on 2017/4/18.
 */
public class DigestUtils {

    private static MessageDigest Md5Instance;

    static {
        try {
            Md5Instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String Md5Encoder(String str) throws UnsupportedEncodingException {
        //确定计算方法
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(Md5Instance.digest(str.getBytes("utf-8")));
    }


    public static String Crc32Encode(InputStream is) {
        CRC32 crc32 = new CRC32();
        CheckedInputStream cis = new CheckedInputStream(is, crc32);
        return Long.toHexString(crc32.getValue());
    }

    public static String Crc32Encode(byte[] data) {
        return Crc32Encode(new ByteArrayInputStream(data));
    }

    public static boolean checkPassword(String inputPassword, String digest) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return Md5Encoder(inputPassword).equals(digest);
    }
}

