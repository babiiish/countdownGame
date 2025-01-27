package controller;

import model.CountdownModel;
import view.CountdownView;

import java.util.List;

public class CountdownController {
    private final CountdownModel model;
    private final CountdownView view;

    public CountdownController(CountdownModel model, CountdownView view) {
        this.model = model;
        this.view = view;
    }

    public void playGame() {
        view.displayIntroduction();

        int numVowels = view.getVowels();
        int numConsonants = 9 - numVowels;

        List<Character> letters = model.generateLetters(numVowels, numConsonants);
        view.displayLetters(letters);
    }
}
