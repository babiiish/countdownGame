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

    /*    */

    /**
     * @param seconds
     *//*
    public void displayCountdown(int seconds) {
        System.out.println("You have "+ seconds + " seconds to think of a word!");
        System.out.print("Time remaining: ");
        for (int i = seconds; i >= 0; i--) {
            System.out.print("\rTime remaining: " + i + " seconds ");
            try {
                Thread.sleep(1000); // Pause for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("\nTimer interrupted!");
                return;
            }
        }
        System.out.println("\rTime is up!                     "); // Clear the line
    }*/
    public String getInputWithCountdown(int seconds) {
        final String[] userInput = {null};
        Thread inputThread = new Thread(() -> {
            System.out.print("Enter your word: ");
            Scanner scanner = new Scanner(System.in);
            userInput[0] = scanner.nextLine();
        });

        Thread countdownThread = new Thread(() -> {
            System.out.println("Countdown started!");
            for (int i = seconds; i >= 0; i--) {
                System.out.print("\rTime remaining: " + i + " seconds  ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (userInput[0] == null) {
                System.out.println("\nTime's up!");
            }
        });

        inputThread.start();
        countdownThread.start();

        try {
            inputThread.join();
            countdownThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error during input handling!");
        }

        return userInput[0];
    }
}
