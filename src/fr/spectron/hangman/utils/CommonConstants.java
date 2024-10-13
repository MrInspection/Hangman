package fr.spectron.hangman.utils;

import java.awt.*;

public class CommonConstants {
    public static final String APP_NAME = "Hangman";

    public static final Dimension FRAME_SIZE = new Dimension(540, 760);
    public static final Dimension BUTTON_PANEL_SIZE = new Dimension(FRAME_SIZE.width, (int) (FRAME_SIZE.height * 0.42));
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension(FRAME_SIZE.width, FRAME_SIZE.height / 6);

    public static final String WORD_DATABASE_PATH = "resources/data.txt";
    public static final String IMAGE_PATH = "/resources/1.png";

    public static final Color PRIMARY_COLOR = Color.decode("#142120");
    public static final Color SECONDARY_COLOR = Color.decode("#FCA311");
    public static final Color BACKGROUND_COLOR = Color.decode("#101820");
}
