package model;

import java.util.*;

public class LetterModel {
    private final List<Character> letters;

    public LetterModel() {
        this.letters = new ArrayList<>();
    }

    public List<Character> generateLetters(int numVowels, int numConsonants) {
        String vowels = "aeiou";
        String consonants = "bcdfghjklmnpqrstvwxyz";

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
