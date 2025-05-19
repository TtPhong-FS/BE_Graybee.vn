package vn.graybee.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceUtil {

    private final MessageSource messageSource;

    public MessageSourceUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String baseKey, String key) {
        String fullKey = baseKey + "." + key;
        return messageSource.getMessage(fullKey, null, getLocale());
    }

    public String get(String baseKey, String key, Object[] args) {
        String fullKey = baseKey + "." + key;
        return messageSource.getMessage(fullKey, args, getLocale());
    }

    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

}
