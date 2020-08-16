package com.sm;

import java.util.Locale;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "VDatePicker", shortName = "VDatePicker", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    public MainView() {
    	
    	this.setSizeFull();
    	
    	VDatePicker datepicker = new VDatePicker("dd-mmm-yyyy");
    	datepicker.setLabel("Date Picker");
    	
    	VDatePicker frdatepicker = new VDatePicker("dd-mmm-yyyy");
    	frdatepicker.setLabel("Date Picker");
    	frdatepicker.setLocale(Locale.FRENCH);
    	
    	VDatePicker timepicker = new VDatePicker();
    	timepicker.setLabel("Time Picker");
    	timepicker.setIncludeDate(false);
    	timepicker.setIncludeTime(true);
    	
    	FormLayout fm = new FormLayout();
    	fm.setWidth("500px");
    	fm.add(datepicker, frdatepicker, timepicker);
    	this.add(fm);
    }
}
