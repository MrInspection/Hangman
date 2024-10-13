package fr.spectron.hangman.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CustomTools {
    public static JLabel loadImage(String resource) {
        BufferedImage image;
        try {
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            image = ImageIO.read(inputStream);
            return new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image from resource: " + resource, e);
        }
    }

    public static void updateImage(JLabel imageContainer, String resource) {
        BufferedImage image;
        try {
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            image = ImageIO.read(inputStream);
            imageContainer.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image from resource: " + resource, e);
        }
    }

    public static String hideWords(String word) {
        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) == ' ')) {
                hiddenWord.append("*");
            } else {
                hiddenWord.append(" ");
            }
        }
        return hiddenWord.toString();
    }
}
