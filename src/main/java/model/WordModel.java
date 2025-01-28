package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * WordModel is a class responsible for handling word-related operations
 */
public class WordModel {
    private final Set<String> wordList;

    public WordModel(String filePath) {
        this.wordList = loadWords(filePath);
    }

    /**
     * Loads a words from file, converting them to lowercase and trimming whitespace.
     *
     * @param filePath The path to the word lists' file, one word per line.
     * @return A set containing valid English words. If an error occurs, returns an empty set.
     */
    private Set<String> loadWords(String filePath) {
        Set<String> words = new HashSet<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String word : lines) {
                words.add(word.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return words;
    }

    /**
     * Finds the longest word that can be made from a list of letters.
     *
     * @param letters A list of characters containing vowels and consonants.
     * @return The longest valid word found in the given words list. If no valid word is found, returns an empty string.
     */
    public String findLongestWord(List<Character> letters) {
        String longestWord = "";
        List<String> letterCombinations = generateCombinations(letters);

        for (String word : letterCombinations) {
            if (wordList.contains(word) && word.length() > longestWord.length()) {
                longestWord = word;
            }
        }
        return longestWord;
    }

    /**
     * Generates all possible combinations of letters.
     *
     * @param letters A list of characters containing vowels and consonants.
     * @return A list of all possible combinations of the letters.
     */
    private List<String> generateCombinations(List<Character> letters) {
        List<String> combinations = new ArrayList<>();
        String input = letters.stream().map(String::valueOf).collect(Collectors.joining());
        int n = input.length();

        for (int length = 1; length <= n; length++) {
            generatePermutations(input.toCharArray(), length, new boolean[n], new StringBuilder(), combinations);
        }
        return combinations;
    }

    /**
     * Recursively generates all permutations of a specified length from an array of characters.
     * taken from <a href="https://stackoverflow.com/questions/10305153/generating-all-possible-permutations-of-a-list-recursively/10305419">...</a>.
     *
     * @param chars An array of characters to generate permutations from.
     * @param length    The target length of the permutations.
     * @param used  A boolean array that keeps track of which characters have already been used in the current permutation.
     * @param current   A StringBuilder that stores the current permutation being built.
     * @param combinations  A list to store all generated permutations.
     */
    private void generatePermutations(char[] chars, int length, boolean[] used, StringBuilder current, List<String> combinations) {
        if (current.length() == length) {
            combinations.add(current.toString());
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            if (!used[i]) {
                used[i] = true;
                current.append(chars[i]);
                generatePermutations(chars, length, used, current, combinations);
                current.deleteCharAt(current.length() - 1);
                used[i] = false;
            }
        }
    }

    /**
     * Checks if a given word is a valid english word by looking it up in the word list.
     *
     * @param word The word to be checked.
     * @return true if the word is found in the word list, otherwise false.
     */
    public boolean isWordEnglishValid(String word) {
        return wordList.contains(word.toLowerCase());
    }

    /**
     * Checks if a word can be formed from a given list of letters.
     *
     * @param letters A list of characters available to form the word.
     * @param word The word to be checked.
     * @return true if the word is formed from letters, otherwise false.
     */
    public boolean isWordFromLetters(List<Character> letters, String word) {
        Map<Character, Integer> letterCounts = new HashMap<>();
        for (char c : letters) {
            letterCounts.put(c, letterCounts.getOrDefault(c, 0) + 1);
        }
        for (char c : word.toCharArray()) {
            if (!letterCounts.containsKey(c) || letterCounts.get(c) == 0) {
                return false;
            }
            letterCounts.put(c, letterCounts.get(c) - 1);
        }
        return true;
    }
}
