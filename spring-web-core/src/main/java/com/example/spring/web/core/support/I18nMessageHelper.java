package com.example.spring.web.core.support;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.example.spring.common.StringUtils;
import com.example.spring.web.core.enums.I18nMessageKey;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class I18nMessageHelper {

    private final static I18nMessageHelper I18N_MESSAGE_HELPER = new I18nMessageHelper();

    private static MessageSource messageSource;

    public static I18nMessageHelper getInstance() {
        messageSource = SpringContext.getBeanByName(MessageSource.class, "messageSource");
        Objects.requireNonNull(messageSource);
        return I18N_MESSAGE_HELPER;
    }

    public Optional<String> getLang() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale != null) {
            return Optional.of(locale.toString());
        }
        return Optional.empty();
    }

    public Optional<String> getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.getMessage(key, locale);
    }

    public Optional<String> getMessage(String key, Locale locale) {
        log.info("locale:{},key:{}", locale, key);
        String message = messageSource.getMessage(key, null, locale);
        if (StringUtils.isNotBlank(message)) {
            return Optional.of(message);
        }
        return Optional.empty();
    }

    public Optional<String> getMessage(I18nMessageKey i18nMessageKey, Object... args) {
        String key = i18nMessageKey.getMessageKey();
        Optional<String> messageOpt = this.getMessage(key);
        if (messageOpt.isPresent()) {
            String message = messageOpt.get();
            message = StrFormatter.format(message, args);
            return Optional.of(message);
        }
        return Optional.empty();
    }

}
