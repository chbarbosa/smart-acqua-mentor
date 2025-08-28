package com.smartacqua.portal;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route("")
@PageTitle("Smart Acqua Mentor Portal")
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(new H1("Welcome to Smart Acqua Mentor"));

        Button aquaristBtn = new Button("Aquarist Module", e ->
                UI.getCurrent().getPage().setLocation("http://localhost:8081")
        );
        Button aquariumBtn = new Button("Aquarium Module", e ->
                UI.getCurrent().getPage().setLocation("http://localhost:8082")
        );
        Button mentorBtn = new Button("Mentor Module", e ->
                UI.getCurrent().getPage().setLocation("http://localhost:8083")
        );
        Button retuneBtn = new Button("Retune Module", e ->
                UI.getCurrent().getPage().setLocation("http://localhost:8084")
        );

        add(aquaristBtn, aquariumBtn, mentorBtn, retuneBtn);
    }
}
