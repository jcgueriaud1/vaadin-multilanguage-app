package org.vaadin.jchristophe.application;

import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

/**
 * See this link for documentation: https://vaadin.com/docs/v14/flow/advanced/tutorial-i18n-localization/#provider-sample-for-translation
 */
@SpringComponent
public class TranslationProvider implements I18NProvider {

    public static final String BUNDLE_PREFIX = "messages";

    public final Locale LOCALE_FR = new Locale("fr", "FR");
    public final Locale LOCALE_EN = new Locale("en", "GB");

    private List<Locale> locales = Collections
            .unmodifiableList(Arrays.asList(LOCALE_FR, LOCALE_EN));

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (key == null) {
            LoggerFactory.getLogger(TranslationProvider.class.getName())
                    .warn("Got lang request for key with null value!");
            return "";
        }

        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);

        String value;
        try {
            value = bundle.getString(key);
        } catch (final MissingResourceException e) {
            LoggerFactory.getLogger(TranslationProvider.class.getName())
                    .warn("Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }
}