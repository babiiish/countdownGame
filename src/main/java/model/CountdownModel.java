package model;

import java.util.*;

public class CountdownModel {
    private final Set<String> wordList;
    private int totalScore;

    public CountdownModel(Set<String> wordList) {
        this.wordList = wordList;
        this.totalScore = 0;
    }

    public List<Character> generateLetters(int numVowels, int numConsonants) {
        String vowels = "aeiou";
        String consonants = "bcdfghjklmnpqrstvwxyz";

        List<Character> letters = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numVowels; i++) {
            letters.add(vowels.charAt(random.nextInt(vowels.length())));
        }

        for (int i = 0; i < numConsonants; i++) {
            letters.add(consonants.charAt(random.nextInt(consonants.length())));
        }

        Collections.shuffle(letters);
        return letters;
    }
}
