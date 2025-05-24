package vn.graybee.common.utils;

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

    public String get(String key) {
        return messageSource.getMessage(key, null, getLocale());
    }

    public String get(String key, Object[] args) {
        return messageSource.getMessage(key, args, getLocale());
    }

    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

}
