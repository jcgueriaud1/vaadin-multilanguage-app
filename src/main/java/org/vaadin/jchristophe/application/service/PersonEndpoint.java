package org.vaadin.jchristophe.application.service;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.connect.Endpoint;
import com.vaadin.flow.server.connect.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.vaadin.jchristophe.application.data.Person;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

/**
 * @author jcgueriaud
 */
@AnonymousAllowed
@Endpoint
public class PersonEndpoint {

    /**
     * Loads a Person to edit into the view.
     * @return default form data
     */
    public Person loadPerson() {
        return new Person();
    }

    public void savePerson(Person person) {
        System.out.println("save the person");
    }
}
