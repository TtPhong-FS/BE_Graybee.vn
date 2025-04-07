package vn.graybee.utils;

import java.security.SecureRandom;

public class UidGenerator {

    public static Integer generateUid() {
        SecureRandom random = new SecureRandom();
        return 100000 + random.nextInt(900000);
    }

}
