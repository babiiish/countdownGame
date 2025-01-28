package controller;

import model.LetterModel;
import model.WordModel;
import view.CountdownView;

import java.util.List;

public class CountdownController {
    private final LetterModel letterModel;
    private final WordModel wordModel;
    private final CountdownView view;

    public CountdownController(LetterModel letterModel, WordModel wordModel, CountdownView view) {
        this.letterModel = letterModel;
        this.wordModel = wordModel;
        this.view = view;
    }

    public void playGame() {
        view.displayIntroduction();

        int numVowels = view.getVowels();
        int numConsonants = 9 - numVowels;

        List<Character> letters = letterModel.generateLetters(numVowels, numConsonants);
        view.displayLetters(letters);

        String longestWord = wordModel.findLongestWord(letters);
        view.displayRoundResult(longestWord, 0);
    }
}
