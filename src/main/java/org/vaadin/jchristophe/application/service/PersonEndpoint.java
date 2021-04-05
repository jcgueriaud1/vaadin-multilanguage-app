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


    private static Validator validator;

    @PostConstruct
    private void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    /**
     * Loads a Person to edit into the view.
     * @return default form data
     */
    public Person loadPerson() {
        return new Person();
    }
    public void savePerson(Person person) {

        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate( person );
        for (ConstraintViolation<Person> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
        if (constraintViolations.isEmpty()) {
            System.out.println("save the person");
        } else {
            System.out.println("Error when save the person");
        }
    }
}
