package org.vaadin.jchristophe.application.views.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.jchristophe.application.data.Person;

@Route(value = "form-flow")
public class FlowFormView extends VerticalLayout implements HasDynamicTitle {

    private Binder<Person> binder = new BeanValidationBinder<>(Person.class);
    private TextField firstName = new TextField(getTranslation("form.person.firstName"));
    private TextField lastName = new TextField(getTranslation("form.person.lastName"));
    private TextField email = new TextField(getTranslation("form.person.email"));
    private Button validateButton = new Button(getTranslation("form.validate"));

    public FlowFormView() {
        add(firstName, lastName, email);
        add(validateButton);
        binder.forField(firstName).bind("firstName");
        binder.forField(lastName).bind("lastName");
        binder.forField(email).bind("email");
        binder.setBean(new Person());
        validateButton.addClickListener(e -> {
            if (binder.validate().isOk()) {
                Notification.show(getTranslation("form.valid"));
            } else {
            Notification.show(getTranslation("form.invalid"));
        }
        });
    }

    @Override
    public String getPageTitle() {
        return getTranslation("flow.form.title");
    }

}
