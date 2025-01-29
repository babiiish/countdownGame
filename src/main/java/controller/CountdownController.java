package controller;

import model.LetterModel;
import model.WordModel;
import view.CountdownView;

import java.util.List;


/**
 * CountdownController handles the interaction between the view and the models: LetterModel, WordModel.
 */
public class CountdownController {
    private static final int ROUND_TIME_LIMIT = 5; // Configurable timer
    private static final int ROUND_LENGTH = 4;
    private final LetterModel letterModel;
    private final WordModel wordModel;
    private final CountdownView view;
    private int score;

    public CountdownController(LetterModel letterModel, WordModel wordModel, CountdownView view) {
        this.letterModel = letterModel;
        this.wordModel = wordModel;
        this.view = view;
    }

    public void playGame() {
        view.displayIntroduction();

        for (int round = 1; round <= ROUND_LENGTH; round++) {
            System.out.println("\nRound " + round);

            int numVowels = view.getVowels();
            int numConsonants = 9 - numVowels;

            List<Character> letters = letterModel.generateLetters(numVowels, numConsonants);
            view.displayLetters(letters);

//            String userWord = view.getInputWithCountdown(ROUND_TIME_LIMIT);
            String userWord = view.getWord();

            if (userWord == null || userWord.isEmpty()) {
                System.out.println("\nNo word entered within the time limit.");
            } else if (!wordModel.isWordFromLetters(letters, userWord)) {
                System.out.println("\nInvalid word! Your word must be formed using the given letters.");
            } else if (!wordModel.isWordEnglishValid(userWord)) {
                System.out.println("\nInvalid word! The word is not found in the English dictionary.");
            } else {
                int wordScore = calculateScore(userWord); // Scoring logic
                score += wordScore;
                System.out.println("\nYou entered: " + userWord + " (Score: " + wordScore + ")");
            }

            String longestWord = wordModel.findLongestWord(letters);

            view.displayRoundResult(longestWord, score);
        }

        // Display the total score after all rounds
        System.out.println("\nGame Over! Your total score is: " + score);
    }

    private int calculateScore(String word) {
        return word.length();
    }
}
