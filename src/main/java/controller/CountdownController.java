package controller;

import model.LetterModel;
import model.PlayerModel;
import model.WordModel;
import view.CountdownView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * CountdownController handles the interaction between the view and the models: LetterModel, WordModel.
 */
public class CountdownController {
    private static final int ROUND_TIME_LIMIT = 5; // Configurable timer
    private static final int ROUND_LENGTH = 4;
    private static final String VOWELS = "aeiou";
    private static final String CONSONANTS = "bcdfghjklmnpqrstvwxyz";

    private final LetterModel letterModel;
    private final WordModel wordModel;
    private final PlayerModel playerModel;

    private final CountdownView view;

    private int possibleScore;
    private final Random random = new Random();

    public CountdownController(LetterModel letterModel, WordModel wordModel, PlayerModel playerModel, CountdownView view) {
        this.letterModel = letterModel;
        this.wordModel = wordModel;
        this.playerModel = playerModel;
        this.view = view;
    }

    public void playGame() {
        view.displayGameState(CountdownView.MessageType.INTRODUCTION);
        playerModel.setName(view.getPlayerName());

        for (int round = 1; round <= ROUND_LENGTH; round++) {
            view.displayGameState(CountdownView.MessageType.DISPLAY_ROUNDS, String.valueOf(round));

//            int numVowels = view.getVowels();
//            int numConsonants = 9 - numVowels;
//            letterModel.setLetters(generateAllLetters(numVowels, numConsonants));

            letterModel.setLetters(generateLetters());

            String userWord = view.getWordWithCountdown(ROUND_TIME_LIMIT);

            int roundScore = validateAndScoreWord(userWord, letterModel.getLetters());
            playerModel.addScore(roundScore);

            String longestWord = wordModel.findLongestWord(letterModel.getLetters());
            possibleScore += longestWord.length();

            playerModel.incrementRounds();
            view.displayGameState(CountdownView.MessageType.USER_WORD_SCORE, userWord, String.valueOf(roundScore), longestWord);
        }

        view.displayGameState(CountdownView.MessageType.FINAL_RESULT, String.valueOf(playerModel.getScore()), String.valueOf(possibleScore));
        displayPreviousScores();
        saveScoreToFile();

        view.closeScanner();
        System.exit(0);
    }

    private int calculateScore(String word) {
        return word.length();
    }

    public void saveScoreToFile() {
        try (FileWriter writer = new FileWriter("src/main/resources/score.txt", true)) {
            writer.write(playerModel.toString() + "\n");  //
            System.out.println("\nScore saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }


    public void displayPreviousScores() {
        List<PlayerModel> players = loadPlayersFromFile();
        players.add(this.playerModel);

        // Filter players by the current round (based on roundsPlayed)
        List<PlayerModel> currentRoundPlayers = players.stream()
                .filter(player -> player.getRoundsPlayed() == playerModel.getRoundsPlayed())
                .sorted((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()))  // Sort by score in descending order
                .limit(5)  // Limit to top 5 players
                .toList();

        boolean foundCurrentPlayer = false;
        int rank = 1;

        System.out.println("\nTop 5 Players:");
        for (PlayerModel player : currentRoundPlayers) {
            if (player.equals(playerModel)) {
                foundCurrentPlayer = true;
                System.out.println("Your current rank: #" + rank + " - " + player.getName() + " - Score: " + player.getScore());
            } else {
                System.out.println("#" + rank + " - " + player.getName() + " - Score: " + player.getScore());
            }
            rank++;
        }

        if (!foundCurrentPlayer) {
            System.out.println("\nYour score: " + playerModel.getScore());
            System.out.println("You are not in the top 5.");
        }
    }

    private List<PlayerModel> loadPlayersFromFile() {
        List<PlayerModel> players = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/score.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                players.add(PlayerModel.fromString(line));  // Convert string to PlayerModel object
            }
        } catch (IOException e) {
            System.out.println("Error reading scores: " + e.getMessage());
        }
        return players;
    }

    private int validateAndScoreWord(String userWord, List<Character> letters) {
        if (userWord == null || userWord.isEmpty()) {
            view.displayErrorMessage(CountdownView.MessageType.NO_WORDS);
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

    /**
     * Generates a list of characters given a specified number of vowels and consonants.
     *
     * @param numVowels The number of vowels.
     * @param numConsonants The number of consonants.
     * @return A shuffled list of characters.
     */
    public List<Character> generateAllLetters(int numVowels, int numConsonants) {
        List<Character> letters = new ArrayList<>(); // Create a new list each time
        for (int i = 0; i < numVowels; i++) {
            letters.add(VOWELS.charAt(random.nextInt(VOWELS.length())));
        }
        for (int i = 0; i < numConsonants; i++) {
            letters.add(CONSONANTS.charAt(random.nextInt(CONSONANTS.length())));
        }
        Collections.shuffle(letters);
        return letters;
    }

    public List<Character> generateLetters() {
        List<Character> letters = new ArrayList<>();
        int vowelsCount = 0;
        int consonantsCount = 0;

        // First, let the player choose letters until the required vowel and consonant count is reached
        while (letters.size() < 9) {
            String choice = view.getVowelOrConsonantChoice();

            if (choice.equals("v") && vowelsCount < 5) {  // Can add vowels only if vowels are less than 5
                letters.add(VOWELS.charAt(random.nextInt(VOWELS.length())));
                vowelsCount++;
            } else if (choice.equals("c") && consonantsCount < 6) {  // Can add consonants only if consonants are less than 6
                letters.add(CONSONANTS.charAt(random.nextInt(CONSONANTS.length())));
                consonantsCount++;
            } else if (!choice.equals("c") && !choice.equals("v")) {
                view.displayErrorMessage(CountdownView.MessageType.INVALID_CHOICE_MESSAGE);
                continue;
            } else {
                view.displayErrorMessage(CountdownView.MessageType.TOO_MANY_VOWELS_OR_CONSONANTS); // Invalid choice handling
                continue;  // Continue prompting the player for valid input
            }

            // Display the current list of letters after each choice
            view.displayGameState(CountdownView.MessageType.DISPLAY_LETTERS, String.valueOf(letters));
        }

        return letters;
    }
}
