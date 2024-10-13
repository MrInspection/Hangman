package fr.spectron.hangman;

import fr.spectron.hangman.utils.CommonConstants;
import fr.spectron.hangman.utils.CustomTools;
import fr.spectron.hangman.utils.WordDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Hangman extends JFrame implements ActionListener {

    private int incorrectGuesses;
    private String[] wordChallenge;

    private final WordDatabase wordDatabase;
    private JLabel hangmanImage, categoryLabel, hiddenWordLabel, resultLabel, wordLabel;
    private final JButton[] letterButtons;
    private JDialog resultDialog;

    public Hangman() {
        super(CommonConstants.APP_NAME);
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        // Init variables
        wordDatabase = new WordDatabase();
        letterButtons = new JButton[26];
        wordChallenge = wordDatabase.loadChallenge();
        createResultDialog();

        addGuiComponents();
    }

    private void addGuiComponents() {
        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);

        // Category
        categoryLabel = new JLabel(wordChallenge[0]);
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(CommonConstants.SECONDARY_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categoryLabel.setBounds(0, hangmanImage.getPreferredSize().height, CommonConstants.FRAME_SIZE.width, 30);

        // Hidden word
        hiddenWordLabel = new JLabel(CustomTools.hideWords(wordChallenge[1]));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBounds(0, categoryLabel.getY() + categoryLabel.getPreferredSize().height + 50,
                CommonConstants.FRAME_SIZE.width, hiddenWordLabel.getPreferredSize().height);

        // Letter buttons
        GridLayout gridLayout = new GridLayout(4, 7);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(-5, hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                CommonConstants.BUTTON_PANEL_SIZE.width, CommonConstants.BUTTON_PANEL_SIZE.height);
        buttonPanel.setLayout(gridLayout);

        for (char letter = 'A'; letter <= 'Z'; letter++) {
            JButton button = new JButton(Character.toString(letter));
            button.setBackground(CommonConstants.PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
            button.addActionListener(this);

            // Usage of ASCII values to calculate the current index
            int currentIndex = letter - 'A';
            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);
        }

        // RESET Button
        JButton resetButton = new JButton("Reset");
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(CommonConstants.SECONDARY_COLOR);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        // QUIT Button
        JButton quitButton = new JButton("Quit");
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(CommonConstants.SECONDARY_COLOR);
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);

        getContentPane().add(hangmanImage);
        getContentPane().add(categoryLabel);
        getContentPane().add(hiddenWordLabel);
        getContentPane().add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Reset")) {
            resetGame();
        }

        if (command.equals("Restart")) {
            resetGame();
            resultDialog.setVisible(false);
        }

        if (command.equals("Quit")) {
            dispose();
        }

        // Disable button logic
        JButton button = (JButton) e.getSource();
        button.setEnabled(false);

        // Check if the guessed letter is correct
        if (wordChallenge[1].contains(command)) {
            button.setBackground(Color.GREEN);
        } else {
            button.setBackground(Color.RED);
            incorrectGuesses++;
            CustomTools.updateImage(hangmanImage, "/resources/" + (incorrectGuesses + 1) + ".png");
            if (incorrectGuesses >= 6) {
                resultLabel.setText("You promised me human intelligence, or is this such a thing?");
                resultDialog.setVisible(true);
            }
        }

        // Store the hidden word in a char array to check if the guessed letter is in the hidden word
        char[] hiddenWord = hiddenWordLabel.getText().toCharArray();
        for (int i = 0; i < wordChallenge[1].length(); i++) {
            if (wordChallenge[1].charAt(i) == command.charAt(0)) {
                hiddenWord[i] = command.charAt(0);
            }
        }

        // Update the hidden word label
        hiddenWordLabel.setText(String.valueOf(hiddenWord));
        if (!hiddenWordLabel.getText().contains("*")) {
            resultLabel.setText("A superior human being with superior intelligence has been found!");
            resultDialog.setVisible(true);
        }

        wordLabel.setText("The word was " + wordChallenge[1]);
    }

    /**
     * Reset the game by loading a new challenge, a new starting image, and update the category and hidden word labels.
     **/

    private void resetGame() {
        wordChallenge = wordDatabase.loadChallenge();
        incorrectGuesses = 0;
        CustomTools.updateImage(hangmanImage, CommonConstants.IMAGE_PATH);
        categoryLabel.setText(wordChallenge[0]);
        String hiddenWord = CustomTools.hideWords(wordChallenge[1]);
        hiddenWordLabel.setText(hiddenWord);

        // Enable all buttons
        for (JButton letterButton : letterButtons) {
            letterButton.setEnabled(true);
            letterButton.setBackground(CommonConstants.PRIMARY_COLOR);
        }
    }

    private void createResultDialog() {
        resultDialog = new JDialog();
        resultDialog.setTitle("Result");
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(3, 1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });

        resultLabel = new JLabel();
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wordLabel = new JLabel();
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(CommonConstants.SECONDARY_COLOR);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(wordLabel);
        resultDialog.add(restartButton);
    }
}
