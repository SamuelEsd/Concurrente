package unam.ciencias.computoconcurrente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.List;
import java.util.ArrayList;

public class BallWorld extends JPanel {
    private static final int _WIDTH = 400;
    private static final int _HEIGHT = 400;
    private static final Color BG_COLOR = Color.white;

    private List<Ball> balls;

    public BallWorld() {
        setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
    	setOpaque(true);
	    setBackground(BG_COLOR);

	    balls = new ArrayList<>();
    }

    public void addBall(final Ball b) {
        balls.add(b);
    }

    public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   balls.forEach((b) -> { b.draw(g); });
    }
}
