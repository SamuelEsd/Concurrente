package unam.ciencias.computoconcurrente;

import java.awt.Color;
import java.awt.Graphics;

public class Ball implements Runnable {

    private final static int WIDTH = 10;
    private final static int HEIGHT = 10;

    private int xPos;
    private int yPos;
    private int xInc;
    private int yInc;
    private final Color color;
    private BallWorld world;

    public Ball(BallWorld world, int xPos, int yPos, int xInc, int yInc, Color color) {
        this.world = world;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xInc = xInc;
        this.yInc = yInc;
        this.color = color;
        world.addBall(this);
    }

    public void run() {
        while(true) {
            move();
            if (world.tryAcquire()) {
                world.purge(this);
                Thread.currentThread().stop();
            }
            else{
                Utils.sleepCurrentThread(1);
            }
        }
    }

    public void move() {
        updateMovementDirection();
        Utils.sleepCurrentThread(30);
        updatePosition();
        world.repaint();
    }

    private void updateMovementDirection() {
        updateHorizontalMovement();
        updateVerticalMovement();
    }

    private void updateHorizontalMovement() {
        if (xPos >= world.getWidth() - WIDTH || xPos <= 0) {
            xInc = -xInc;
        }
    }

    private void updateVerticalMovement() {
        if (yPos >= world.getHeight() - HEIGHT || yPos <= 0) {
            yInc = -yInc;
        }
    }

    public synchronized void updatePosition() {
        xPos += xInc;
        yPos += yInc;
    }

    public synchronized void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(xPos, yPos, WIDTH, HEIGHT);
    }

    public Color getColor() {
        return color;
    }
}
