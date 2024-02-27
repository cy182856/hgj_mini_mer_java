package com.ej.hgj.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Codec {
    private static final String PASSWORD_SEED = "35d79cb9f64b11b1625795e9cb9ee461";

    public Codec() {
    }

    public String encodePassword(String clearPassword) {
        return DigestUtils.md5Hex("35d79cb9f64b11b1625795e9cb9ee461" + clearPassword);
    }

    public boolean isPasswordEqual(String clearPassword, String encodedPassword) {
        if (encodedPassword != null && clearPassword != null) {
            return encodedPassword.equals(this.encodePassword(clearPassword));
        } else {
            return false;
        }
    }

    public String getUniqIdHash() {
        return UniqID.getInstance().getUniqIDHash();
    }

    public String getNewPassword() {
        return this.getRandomString(6, false);
    }

    public String getRandomString(int length, boolean allowLetter) {
        if (length < 1) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer(length);

            for(int i = 0; i < length; ++i) {
                sb.append(allowLetter ? this.genRandomChar(i != 0) : this.genRandomDigit(i != 0));
            }

            return sb.toString();
        }
    }

    protected char genRandomChar(boolean allowZero) {
        boolean var2 = false;

        int randomNumber;
        do {
            randomNumber = (int)(Math.random() * 36.0D);
        } while(randomNumber == 0 && !allowZero);

        return randomNumber < 10 ? (char)(randomNumber + 48) : (char)(randomNumber - 10 + 97);
    }

    protected char genRandomDigit(boolean allowZero) {
        boolean var2 = false;

        int randomNumber;
        do {
            randomNumber = (int)(Math.random() * 10.0D);
        } while(randomNumber == 0 && !allowZero);

        return (char)(randomNumber + 48);
    }
}
