package com.smartacqua;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.HasValidationProperties;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldBase;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Optional;
@Route("")
@PageTitle("Aquarist Profile")
public class AquaristView extends VerticalLayout {

    public AquaristView(AquaristService service, SessionStore session) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField name = createNameField();

        EmailField email = new EmailField("Email");
        email.setRequiredIndicatorVisible(true);
        email.setErrorMessage("Please enter a valid email");

        TextField phone = new TextField("Phone");
        phone.setPattern("\\d{10,15}");
        phone.setErrorMessage("Phone must be numeric (10–15 digits)");

        PasswordField password = createPasswordField();

        Button actionButton = new Button();
        Span result = new Span();

        String code = session.getAquaristCode();

        if (code != null && !code.isBlank()) {
            Optional<Aquarist> opt = service.findByCode(code);
            if (opt.isPresent()) {
                Aquarist a = opt.get();
                name.setValue(a.getName());
                email.setValue(a.getEmail());
                phone.setValue(a.getPhone());
                actionButton.setText("Update");

                actionButton.addClickListener(e -> {
                    if (validForm(name, phone, email, password)) {
                        service.updateAquarist(code, name.getValue(), email.getValue(), phone.getValue(), password.getValue());
                        result.setText("Profile updated.");
                    }
                });
            } else {
                result.setText("Aquarist not found.");
            }
        } else {
            actionButton.setText("Register");

            actionButton.addClickListener(e -> {
                if (validForm(name, phone, email, password)) {
                    Aquarist a = service.register(name.getValue(), email.getValue(), phone.getValue(), password.getValue());
                    session.setAquaristCode(a.getCode());
                    result.setText("Registration successful.");
                }
            });
        }

        add(new H2("Aquarist Profile"), name, email, phone, password, actionButton, result);
    }

    private static boolean validForm(HasValidationProperties... fields) {
        for (var field : fields) {
            if (field.isInvalid()) {
                Notification.show("Please correct the errors before proceeding", 3000,
                        Notification.Position.MIDDLE);
                return true;
            }
        }
        return false;
    }

    private static PasswordField createPasswordField() {
        PasswordField password = new PasswordField("Password");
        password.setRequiredIndicatorVisible(true);

        password.setValueChangeMode(ValueChangeMode.EAGER);
        password.addValueChangeListener(e -> {
            String value = e.getValue();
            boolean valid = value != null && value.length() >= 8;
            password.setInvalid(!valid);
            password.setErrorMessage("Password must be at least 8 characters");
        });

        return password;
    }

    private static TextField createNameField() {
        TextField name = new TextField("Name");
        name.setRequiredIndicatorVisible(true);

        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(e -> {
            String value = e.getValue();
            boolean valid = value != null && value.length() >= 6 && value.contains(" ");
            name.setInvalid(!valid);
            name.setErrorMessage("Name must be at least 6 characters and contain a space");
        });

        return name;
    }
}

