package com.smartacqua;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("register")
@PageTitle("Aquarist Registration")
public class AquaristView extends VerticalLayout {

    public AquaristView(AquaristService service, SessionStore session) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);

        H2 title = new H2("Register as Aquarist");

        TextField name = new TextField("Name");
        TextField email = new TextField("Email");
        TextField phone = new TextField("Phone");
        PasswordField password = new PasswordField("Password");

        Button register = new Button("Register");
        Span result = new Span();

        register.addClickListener(e -> {
            Aquarist aquarist =
                    service.register(name.getValue(), email.getValue(), phone.getValue(), password.getValue());
            session.setAquaristCode(aquarist.getCode());
            result.setText("Registration successful.");
        });

        add(title, name, email, phone, register, result);
    }
}


