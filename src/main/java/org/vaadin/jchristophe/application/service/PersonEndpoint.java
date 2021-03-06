package org.vaadin.jchristophe.application.service;

import com.vaadin.flow.server.connect.Endpoint;
import com.vaadin.flow.server.connect.auth.AnonymousAllowed;
import org.vaadin.jchristophe.application.data.Person;

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
