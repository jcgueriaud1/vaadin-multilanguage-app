package org.vaadin.jchristophe.application.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * @author jcgueriaud
 */
public class Person {

    @Size(min = 3, max = 250)
    private String firstName;

    /** You can customize your messages here **/
    @Size(min = 3, max = 250, message = "{custom.Size.message}")
    private String lastName;

    @Email
    private String email;

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
