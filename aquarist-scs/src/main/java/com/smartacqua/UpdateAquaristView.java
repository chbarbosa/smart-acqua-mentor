package com.smartacqua;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

@Route("update")
@PageTitle("Aquarist Profile")
public class UpdateAquaristView extends VerticalLayout implements BeforeEnterObserver {

    private final AquaristService service;
    private final SessionStore session;

    public AquaristView(AquaristService service, SessionStore session) {
        this.service = service;
        this.session = session;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String code = event.getLocation().getQueryParameters().getParameters()
                .getOrDefault("aquaristCode", List.of(""))
                .get(0);

        if (code == null || code.isBlank()) {
            event.forwardTo("unauthorized");
            return;
        }

        session.setAquaristCode(code);
        Optional<Aquarist> opt = service.findByCode(code);

        if (opt.isEmpty()) {
            event.forwardTo("unauthorized");
            return;
        }

        Aquarist aquarist = opt.get();
        buildForm(aquarist);
    }

    private void buildForm(Aquarist aquarist) {
        TextField name = new TextField("Name", aquarist.getName());
        TextField email = new TextField("Email", aquarist.getEmail());
        TextField phone = new TextField("Phone", aquarist.getPhone());
        PasswordField password = new PasswordField("New Password");

        Button save = new Button("Update");

        save.addClickListener(e -> {
            service.updateAquarist(session.getAquaristCode(), name.getValue(), email.getValue(), phone.getValue(), password.getValue());
            Notification.show("Profile updated");
        });

        add(name, email, phone, password, save);
    }
}
