package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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

    public String getInputWithCountdown(int seconds) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        AtomicReference<String> userInput = new AtomicReference<>(null);
        AtomicBoolean inputEntered = new AtomicBoolean(false);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("You have " + seconds + " seconds to form a word!");
        System.out.print("Enter your word: \n");

        // Task to read user input
        scheduler.schedule(() -> {
            try {
                String input = br.readLine();
                if (input != null && !input.isEmpty()) {
                    userInput.set(input.trim());
                    inputEntered.set(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, TimeUnit.SECONDS);

        // Task to handle timeout
        scheduler.schedule(() -> {
            if (!inputEntered.get()) {
                inputEntered.set(true); // Mark input as handled
            }
        }, seconds, TimeUnit.SECONDS);

        try {
            scheduler.awaitTermination(seconds + 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        scheduler.shutdown(); // Ensure scheduler stops

        return userInput.get() != null ? userInput.get() : "";
    }
}
