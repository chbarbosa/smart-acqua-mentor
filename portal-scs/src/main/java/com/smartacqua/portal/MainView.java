package com.smartacqua.portal;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
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

        GridLayout grid = new GridLayout(2, 2);
        grid.setWidth("100%");
        //grid.setSpacing(true);

        grid.add(createCard("Aquarist", "Manage your profile", "👤", "#E3F2FD", "http://localhost:8081"));
        grid.add(createCard("Aquarium", "Track your tanks", "🐠", "#E8F5E9", "http://localhost:8082"));
        grid.add(createCard("Mentor", "Get AI advice", "🧠", "#FFF3E0", "http://localhost:8083"));
        grid.add(createCard("Retune", "Switch your AI model", "🔄", "#F3E5F5", "http://localhost:8084"));

        add(grid);
    }

    private Div createCard(String title, String description, String icon, String bgColor, String link) {
        Div card = new Div();
        card.getStyle()
                .set("background-color", bgColor)
                .set("border-radius", "12px")
                .set("padding", "20px")
                .set("box-shadow", "0 2px 6px rgba(0,0,0,0.1)")
                .set("cursor", "pointer")
                .set("transition", "transform 0.2s ease-in-out")
                .set("text-align", "center");

        card.addClickListener(e -> UI.getCurrent().getPage().setLocation(link));

        card.getElement().executeJs(
                "this.addEventListener('mouseenter', () => this.style.transform = 'scale(1.03)');" +
                        "this.addEventListener('mouseleave', () => this.style.transform = 'scale(1)');"
        );

        H2 titleLabel = new H2(icon + " " + title);
        titleLabel.getStyle().set("margin", "0").set("font-size", "20px");

        Span descLabel = new Span(description);
        descLabel.getStyle().set("font-size", "14px").set("color", "#555");

        card.add(titleLabel, descLabel);
        return card;
    }

    // Helper layout for 2x2 grid
    private static class GridLayout extends Div {
        public GridLayout(int cols, int rows) {
            getStyle()
                    .set("display", "grid")
                    .set("grid-template-columns", "1fr 1fr")
                    .set("gap", "20px")
                    .set("padding", "20px");
        }
    }
}

