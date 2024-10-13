package fr.spectron.hangman;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Hangman().setVisible(true));
    }
}
