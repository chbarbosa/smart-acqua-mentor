package com.smartacqua.portal;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Route("login")
@PageTitle("Aquarist Login")
public class LoginView extends VerticalLayout {

    public LoginView(SessionStore session) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H2 title = new H2("Login to Smart Acqua Mentor");

        TextField email = new TextField("Email");
        PasswordField password = new PasswordField("Password");
        Button loginBtn = new Button("Login");

        Span result = new Span();

        loginBtn.addClickListener(e -> {
            try {
                String code = authenticate(email.getValue(), password.getValue());
                session.setAquaristCode(code);
                result.setText("Login successful.");
                UI.getCurrent().getPage().setLocation("http://localhost:8082?aquaristCode=" + code);
            } catch (Exception ex) {
                result.setText("Login failed: " + ex.getMessage());
            }
        });

        add(title, email, password, loginBtn, result);
    }

    private String authenticate(String email, String password) {
        RestTemplate rest = new RestTemplate();
        Map<String, String> payload = Map.of("email", email, "password", password);
        ResponseEntity<Map> response = rest.postForEntity("http://localhost:8081/api/login", payload, Map.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return (String) response.getBody().get("code");
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
