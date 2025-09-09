package com.smartacqua;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
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

        TextField name = new TextField("Name");
        TextField email = new TextField("Email");
        TextField phone = new TextField("Phone");
        PasswordField password = new PasswordField("Password");

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
                    service.updateAquarist(code, name.getValue(), email.getValue(), phone.getValue(), password.getValue());
                    result.setText("Profile updated.");
                });
            } else {
                result.setText("Aquarist not found.");
            }
        } else {
            actionButton.setText("Register");

            actionButton.addClickListener(e -> {
                Aquarist a = service.register(name.getValue(), email.getValue(), phone.getValue(), password.getValue());
                session.setAquaristCode(a.getCode());
                result.setText("Registration successful.");
            });
        }

        add(new H2("Aquarist Profile"), name, email, phone, password, actionButton, result);
    }
}

