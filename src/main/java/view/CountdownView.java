package view;

import java.util.List;
import java.util.Scanner;

/**
 * CountdownView handles the user interface for the Countdown Letter Game.
 */
public class CountdownView {
    private final Scanner scanner;

    public CountdownView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the introduction at beginning of the game.
     */
    public void displayIntroduction() {
        System.out.println("Welcome to the Countdown Letter Game!");
    }

    /**
     * Prompts user to enter the number of vowels.
     *
     * @return Integer inputted by user.
     */
    public int getVowels() {
        System.out.println("Enter the number of vowels (1-3): ");
        return scanner.nextInt();
    }

    /**
     * Displays the letters generated for the user.
     */
    public void displayLetters(List<Character> letters) {
        System.out.println("Your letters are: " + letters);
    }

    /**
     * Displays the results for the round including score and longest word.
     */
    public void displayRoundResult(String longestWord, int score) {
        System.out.println("Longest word: " + longestWord + " (Score: " + score + ")");
    }

    /**
     * Prompts user to input a word.
     *
     * @return Word entered by the user
     */
    public String getWord() {
        final String[] userInput = {null};

        System.out.print("\nEnter your word: ");
        Scanner scanner = new Scanner(System.in);
        userInput[0] = scanner.nextLine();

        return userInput[0];
    }
}
