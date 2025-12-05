package vn.microservice.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Message Utils
 *
 * @author Nong Hoang Vu
 */
@Component
public class MessageUtils {
    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * Setter method for Spring to automatically inject MessageSource
     *
     * @param messageSource MessageSource
     */
    public MessageUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Method to get message with parameters
     *
     * @param code code
     * @param args args
     * @return msg
     */
    public String getMessage(String code, Object args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, new Object[]{args}, locale);
    }

    /**
     * The method to get the message has no parameters
     *
     * @param code code
     * @return msg
     */
    public String getMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }
}