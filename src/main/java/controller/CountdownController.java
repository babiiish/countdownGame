package controller;

import model.LetterModel;
import model.WordModel;
import view.CountdownView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * CountdownController handles the interaction between the view and the models: LetterModel, WordModel.
 */
public class CountdownController {
    private static final int ROUND_TIME_LIMIT = 5; // Configurable timer
    private static final int ROUND_LENGTH = 2;
    private final LetterModel letterModel;
    private final WordModel wordModel;
    private final CountdownView view;
    private int score;
    private int possibleScore;

    public CountdownController(LetterModel letterModel, WordModel wordModel, CountdownView view) {
        this.letterModel = letterModel;
        this.wordModel = wordModel;
        this.view = view;
    }

    public void playGame() {
        view.displayGameState(CountdownView.MessageType.INTRODUCTION);

        for (int round = 1; round <= ROUND_LENGTH; round++) {
            view.displayGameState(CountdownView.MessageType.DISPLAY_ROUNDS, String.valueOf(round));

            int numVowels = view.getVowels();
            int numConsonants = 9 - numVowels;

            List<Character> letters = letterModel.generateLetters(numVowels, numConsonants);
            view.displayGameState(CountdownView.MessageType.DISPLAY_LETTERS, String.valueOf(letters));

            String userWord = view.getWordWithCountdown(ROUND_TIME_LIMIT);

            int roundScore = validateAndScoreWord(userWord, letters);
            score += roundScore;

            String longestWord = wordModel.findLongestWord(letters);
            possibleScore += longestWord.length();

            view.displayGameState(CountdownView.MessageType.USER_WORD_SCORE, userWord, String.valueOf(score), longestWord);
        }

        view.displayGameState(CountdownView.MessageType.FINAL_RESULT, String.valueOf(score), String.valueOf(possibleScore));
        displayPreviousScores();
        saveScoreToFile(score);
    }

    private int calculateScore(String word) {
        return word.length();
    }

    public void saveScoreToFile(int score) {
        try (FileWriter writer = new FileWriter("src/main/resources/score.txt", true)) {  // Append mode
            writer.write("[Player] score: " + score + "\n");
            System.out.println("Score saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    public void displayPreviousScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/score.txt"))) {
            System.out.println("\nPrevious Scores:");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No previous scores found.");
        }
    }

    private int validateAndScoreWord(String userWord, List<Character> letters) {
        if (userWord == null || userWord.isEmpty()) {
            view.displayErrorMessage(CountdownView.MessageType.INVALID_WORD_FROM_LETTERS);
            return 0;
        } else if (!wordModel.isWordFromLetters(letters, userWord)) {
            view.displayErrorMessage(CountdownView.MessageType.INVALID_WORD_FROM_LETTERS);
            return 0;
        } else if (!wordModel.isWordEnglishValid(userWord)) {
            view.displayErrorMessage(CountdownView.MessageType.WORD_NOT_ENGLISH);
            return 0;
        } else {
            // Scoring logic
            return calculateScore(userWord);
        }
    }
}
