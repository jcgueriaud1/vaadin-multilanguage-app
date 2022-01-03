package org.vaadin.jchristophe.application;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.vaadin.artur.helpers.LaunchUtil;
import com.vaadin.flow.theme.Theme;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the * and some desktop browsers.
 *
 */
@NpmPackage(value = "mobx", version = "^6.3.0")
@NpmPackage(value = "@adobe/lit-mobx", version = "^2.0.0")
@NpmPackage(value = "lit-translate", version = "2.0.0-rc.1")
@SpringBootApplication
@Theme(value = "multi-langapp")
@PWA(name = "Multi-lang App", shortName = "Multi-lang App", offlineResources = {"images/logo.png"})
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
    }

}
