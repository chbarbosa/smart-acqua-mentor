package com.smartacqua;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

        Button register = new Button("Generate Code");
        Span result = new Span();

        register.addClickListener(e -> {
            AquaristDTO dto = service.register(name.getValue(), email.getValue(), phone.getValue());
            session.setAquaristCode(dto.getCode());
            result.setText("Your aquarist code: " + dto.getCode());
        });

        add(title, name, email, phone, register, result);
    }
}


