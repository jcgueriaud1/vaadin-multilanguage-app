package org.vaadin.jchristophe.application.views.flowabout;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.server.VaadinService;

import java.util.Locale;

/**
 * Translation for flow:
 * - Configure the Translation provider
 * - Use getTranslation
 */
@Route(value = "about-flow")
public class FlowAboutView extends VerticalLayout implements HasDynamicTitle {

    public FlowAboutView() {
        addClassName("flow-about-view");
        add(new Paragraph(getTranslation("aboutview.text")));
        add(new Paragraph(getTranslation("aboutview.textWithParameter", 1)));
        // create language switcher in Java
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        add(horizontalLayout);
        VaadinService.getCurrent().getInstantiator().getI18NProvider().getProvidedLocales()
                .forEach(locale -> horizontalLayout.add(createLanguageButton(locale)));
    }

    /**
     * Create a button that change the language and reload the page
     * @param locale
     * @return
     */
    private Button createLanguageButton(Locale locale) {
        return new Button(getTranslation("language."+locale.toString()),e -> {
            // set the new locale in the user session and reload the page
            getUI().ifPresent(ui -> {
                ui.getSession().setLocale(locale);
                ui.getPage().reload();
            });
        });
    }

    /**
     * Translate the title
     * @return
     */
    @Override
    public String getPageTitle() {
        return getTranslation("aboutview.title");
    }
}
