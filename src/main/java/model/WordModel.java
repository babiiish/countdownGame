package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class WordModel {
    private final Set<String> wordList;

    public WordModel(String filePath) {
        this.wordList = loadWords(filePath);
    }

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

    private List<String> generateCombinations(List<Character> letters) {
        List<String> combinations = new ArrayList<>();
        String input = letters.stream().map(String::valueOf).collect(Collectors.joining());
        int n = input.length();

        for (int length = 1; length <= n; length++) {
            generatePermutations(input.toCharArray(), length, new boolean[n], new StringBuilder(), combinations);
        }
        return combinations;
    }

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
}
