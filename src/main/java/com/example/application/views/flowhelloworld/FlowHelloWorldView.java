package com.example.application.views.flowhelloworld;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.theme.Theme;

@Theme
@Route(value = "hello-flow")
@PageTitle("Flow - Hello World")
@CssImport("./views/flowhelloworld/flow-hello-world-view.css")
public class FlowHelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public FlowHelloWorldView() {
        addClassName("flow-hello-world-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
