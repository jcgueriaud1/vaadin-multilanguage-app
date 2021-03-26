package com.example.application.views.flowabout;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "about-flow")
@PageTitle("Flow - About")
@CssImport("./views/flowabout/flow-about-view.css")
public class FlowAboutView extends Div {

    public FlowAboutView() {
        addClassName("flow-about-view");
        add(new Text("Content placeholder"));
    }

}
