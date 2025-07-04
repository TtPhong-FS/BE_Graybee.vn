package vn.graybee.common.utils;

import java.security.SecureRandom;

public class CodeGenerator {

    public static final String DIGITS = "0123456789";

    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static final String ALPHANUMERIC = LETTERS + DIGITS;

    private static final SecureRandom random = new SecureRandom();

    public static String generateSessionId(int length, int count, String charset) {
        StringBuilder sessionId = new StringBuilder();

        for (int i = 0; i < count; i++) {
            if (i > 0) sessionId.append("-");
            for (int j = 0; j < length; j++) {
                int index = random.nextInt(charset.length());
                sessionId.append(charset.charAt(index));
            }
        }

        return sessionId.toString();
    }

    public static String generateCode(int length, String charset) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charset.length());
            sb.append(charset.charAt(index));
        }

        return sb.toString();
    }

    public static Integer generateOtp() {
        return random.nextInt(100_000, 899_999);
    }

}
