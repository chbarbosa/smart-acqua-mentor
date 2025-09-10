package com.smartacqua;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
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
import java.util.Timer;
import java.util.TimerTask;

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
                actionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);

                actionButton.getElement().getClassList().add("fade-in");
                actionButton.addClickListener(e -> {
                    actionButton.getElement().getClassList().add("bounce");
                    // remove bounce after animation
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getUI().ifPresent(ui -> ui.access(() -> {
                                actionButton.getElement().getClassList().remove("bounce");
                            }));
                        }
                    }, 400);
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
            actionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

            actionButton.getElement().getClassList().add("fade-in");
            actionButton.addClickListener(e -> {
                actionButton.getElement().getClassList().add("bounce");
                // remove bounce after animation
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getUI().ifPresent(ui -> ui.access(() -> {
                            actionButton.getElement().getClassList().remove("bounce");
                        }));
                    }
                }, 400);
                if (validForm(name, phone, email, password)) {
                    Aquarist a = service.register(name.getValue(), email.getValue(), phone.getValue(), password.getValue());
                    session.setAquaristCode(a.getCode());
                    result.setText("Registration successful.");
                }
            });
        }

        setWidthFull(name, email, phone, password);

        Div formContainer = createContainer();

        VerticalLayout formLayout =
                new VerticalLayout(name, email, phone, password, actionButton, result);
        formLayout.setSpacing(true);
        formLayout.setPadding(false);
        formLayout.setWidthFull();

        formContainer.add(formLayout);

        add(new H2("Aquarist Profile"), formContainer, actionButton, result);
    }

    private void setWidthFull(HasSize... fields) {
        for (var field : fields) {
            field.setWidthFull();
        }
    }

    private static Div createContainer() {
        Div formContainer = new Div();
        formContainer.getStyle().set("max-width", "500px");
        formContainer.getStyle().set("width", "100%");
        formContainer.getStyle().set("padding", "2rem");
        formContainer.getStyle().set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        formContainer.getStyle().set("border-radius", "8px");
        formContainer.getStyle().set("background", "white");
        return formContainer;
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

