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
        view.displayIntroduction();

        for (int round = 1; round <= ROUND_LENGTH; round++) {
            System.out.println("\nRound " + round + "\n");

            int numVowels = view.getVowels();
            int numConsonants = 9 - numVowels;

            List<Character> letters = letterModel.generateLetters(numVowels, numConsonants);
            view.displayLetters(letters);

            String userWord = view.getWordWithCountdown(ROUND_TIME_LIMIT);

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
            possibleScore += longestWord.length();

            view.displayRoundResult(longestWord, score);
        }

        view.displayFinalResult(score, possibleScore);
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
}
