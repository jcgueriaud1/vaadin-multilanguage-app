package org.vaadin.jchristophe.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.vaadin.flow.server.connect.Endpoint;
import com.vaadin.flow.server.connect.auth.AnonymousAllowed;
import org.vaadin.jchristophe.application.data.Person;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.*;

/**
 * @author jcgueriaud
 */
@AnonymousAllowed
@Endpoint
public class TranslationEndpoint {

    public static final String BUNDLE_PREFIX = "messages";

    public final Locale LOCALE_FR = new Locale("fr", "FR");
    public final Locale LOCALE_EN = new Locale("en", "GB");

    private List<Locale> locales = Collections
            .unmodifiableList(Arrays.asList(LOCALE_FR, LOCALE_EN));

    private JavaPropsMapper mapper = new JavaPropsMapper();
    private ObjectMapper objectMapper = new ObjectMapper();

    public String getTranslations(String language) throws IOException {
        Locale locale = LOCALE_EN;
        if (language.equals(LOCALE_FR.toString())) {
            locale = LOCALE_FR;
        }
        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);

        Properties props = convertResourceBundleToProperties(bundle);
        return objectMapper.writeValueAsString(mapper.readPropertiesAs(props, JsonNode.class));
    }


    /**
     * Convert ResourceBundle into a Properties object.
     *
     * @param resource a resource bundle to convert.
     * @return Properties a properties version of the resource bundle.
     */
    private static Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Properties properties = new Properties();
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }
        return properties;
    }
}
