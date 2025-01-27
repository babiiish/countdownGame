package controller;

import model.CountdownModel;
import view.CountdownView;

public class CountdownController {
    private final CountdownModel model;
    private final CountdownView view;

    public CountdownController(CountdownModel model, CountdownView view) {
        this.model = model;
        this.view = view;
    }

    public void playGame() {
        view.displayIntroduction();
    }
}
