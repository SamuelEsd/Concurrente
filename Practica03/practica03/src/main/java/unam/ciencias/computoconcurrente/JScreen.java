package unam.ciencias.computoconcurrente;

import java.awt.*;
import javax.swing.*;

public class JScreen implements ScreenHW {
    private JFrame win;
    private CharDisplay[][] text;
    private static int ROWS = 20;
    private static int COLS = 30;

    public JScreen() {
        win = new JFrame();
        win.getContentPane().setLayout(new GridLayout(ROWS,COLS));
        text = new CharDisplay [ROWS][COLS];
        for(int i=0; i<ROWS; i++)
            for (int j=0; j<COLS; j++) {
                text[i][j] = new CharDisplay();
                win.getContentPane().add(text[i][j]);
            }
        win.pack();
        win.setVisible(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public int getRows() {
        return ROWS;
    }

    @Override
    public int getColumns() {
        return COLS;
    }

    @Override
    public void write(char character, int row, int column) {
        text[row][column].write(character);
        win.getContentPane().validate();
        win.getContentPane().repaint();
    }
}

class CharDisplay extends JLabel {

    CharDisplay() {
        setText(" ");
        setFont(new Font("Monospaced",Font.BOLD,20));
        setBackground(Color.GRAY);
        setForeground(Color.YELLOW);
        setOpaque(true);
    }

    void write(final char c) {
        SwingUtilities.invokeLater(() -> setText(Character.toString(c)));
    }
}