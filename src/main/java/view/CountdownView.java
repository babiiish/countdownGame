package view;

import java.util.List;
import java.util.Scanner;

public class CountdownView {
    private final Scanner scanner;

    public CountdownView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayIntroduction() {
        System.out.println("Welcome to the Countdown Letter Game!");
    }

    public int getVowels() {
        System.out.println("Enter the number of vowels (1-3): ");
        return scanner.nextInt();
    }

    public void displayLetters(List<Character> letters) {
        System.out.println("Your letters are: " + letters);
    }

    public void displayRoundResult(String longestWord, int score) {
        System.out.println("Longest word: " + longestWord + " (Score: " + score + ")");
    }
}
