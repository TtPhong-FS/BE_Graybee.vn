package vn.graybee.common.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtil {

    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");

    public static String toSlug(String input) {
        String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);

        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase();
    }

}
