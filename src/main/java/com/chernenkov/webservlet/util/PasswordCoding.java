package com.chernenkov.webservlet.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCoding {
    public static String encryptPassword(String password) {
        return DigestUtils.md5Hex(password);
    }
}
