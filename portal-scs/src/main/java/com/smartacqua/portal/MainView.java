package com.smartacqua.portal;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route("")
@PageTitle("Smart Acqua Mentor Portal")
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        add(new H1("Smart Acqua Mentor"));

        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setSpacing(true);
        cardLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        cardLayout.add(
                createModuleCard("Aquarist", "Manage your profile", "http://localhost:8081"),
                createModuleCard("Aquarium", "Track your tanks", "http://localhost:8082"),
                createModuleCard("Mentor", "Get AI advice", "http://localhost:8083"),
                createModuleCard("Retune", "Switch your AI model", "http://localhost:8084")
        );

        add(cardLayout);
    }

    private VerticalLayout createModuleCard(String title, String description, String link) {
        VerticalLayout card = new VerticalLayout();
        card.setWidth("220px");
        card.setPadding(true);
        card.setSpacing(false);
        card.getStyle().set("border", "1px solid #ccc");
        card.getStyle().set("border-radius", "8px");
        card.getStyle().set("box-shadow", "2px 2px 8px rgba(0,0,0,0.1)");
        card.getStyle().set("padding", "16px");
        card.getStyle().set("text-align", "center");

        H1 cardTitle = new H1(title);
        cardTitle.getStyle().set("font-size", "20px");
        cardTitle.getStyle().set("margin-bottom", "8px");

        Button accessButton = new Button("Access", e ->
                UI.getCurrent().getPage().setLocation(link)
        );

        card.add(cardTitle, new com.vaadin.flow.component.html.Span(description), accessButton);
        return card;
    }
}
