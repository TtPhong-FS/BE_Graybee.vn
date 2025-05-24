package vn.graybee.common.utils;

import java.security.SecureRandom;

public class CodeGenerator {

    public static final String DIGITS = "0123456789";

    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static final String ALPHANUMERIC = LETTERS + DIGITS;

    private static final SecureRandom random = new SecureRandom();

    public static String generateCode(int length, String charset) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charset.length());
            sb.append(charset.charAt(index));
        }

        return sb.toString();
    }

}
