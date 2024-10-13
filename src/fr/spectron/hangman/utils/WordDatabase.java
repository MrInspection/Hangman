package fr.spectron.hangman.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordDatabase {
    private final HashMap<String, String[]> wordList;
    private final ArrayList<String> categories;

    public WordDatabase() {
        try {
            wordList = new HashMap<>();
            categories = new ArrayList<>();

            String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource(CommonConstants.WORD_DATABASE_PATH)).getPath();

            if (filePath.contains("%20")) filePath = filePath.replaceAll("%20", " ");
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String category = parts[0]; // First word of each line is the category
                categories.add(category);

                String[] values = Arrays.copyOfRange(parts, 1, parts.length);
                wordList.put(category, values);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] loadChallenge() {
        Random rand = new Random();

        // Generates random number to choose category
        String category = categories.get(rand.nextInt(categories.size()));

        // Generates random number to choose the value from the category
        String[] categoryValues = wordList.get(category);
        String word = categoryValues[rand.nextInt(categoryValues.length)];

        return new String[]{category.toUpperCase(), word.toUpperCase()};
    }
}
