package vn.graybee.utils;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class CodeGenerator {

    private static final int LETTER_COUNT = 3;

    private static final int DIGIT_COUNT = 7;

    private static final int TOTAL_LENGTH = LETTER_COUNT + DIGIT_COUNT;

    private static final SecureRandom random = new SecureRandom();

    public static Integer generateUid() {
        SecureRandom random = new SecureRandom();
        return 100000 + random.nextInt(900000);
    }

    public static String trackingGenerate() {
        StringBuilder sb = new StringBuilder(TOTAL_LENGTH);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < LETTER_COUNT; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            sb.append(letter);
        }

        for (int i = 0; i < DIGIT_COUNT; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static Integer generateOtp() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(100_000, 999_999);
    }


}
