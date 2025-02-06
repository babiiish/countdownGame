package model;

import java.util.ArrayList;
import java.util.List;

/**
 * LetterModel is a class responsible for handling letter-related operations
 */
public class LetterModel {
    private List<Character> letters;

    public LetterModel() {
        this.letters = new ArrayList<>();
    }

    public List<Character> getLetters() {
        return letters;
    }

    public void setLetters(List<Character> letters) {
        this.letters = letters;
    }
}
