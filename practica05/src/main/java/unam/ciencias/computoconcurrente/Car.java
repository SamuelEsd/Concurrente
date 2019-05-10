package unam.ciencias.computoconcurrente;

import java.awt.*;

public class Car implements Runnable {
    final static int RED_CAR = 0;
    final static int BLUE_CAR = 1;
    private final static int BRIDGE_Y = 95;
    private final static int BRIDGE_X_LEFT = 210;
    private final static int BRIDGE_X_LEFT_2 = 290;
    private final static int BRIDGE_X_MID = 410;
    private final static int BRIDGE_X_RIGHT_2 = 530;
    private final static int BRIDGE_X_RIGHT = 610;
    private final static int TOTAL_WIDTH = 900;
    private final static int INIT_X[] = new int[]{-80, TOTAL_WIDTH};
    private final static int INIT_Y[] = new int[]{135, 55};
    private final static int OUT_LEFT = -200;
    private final static int OUT_RIGHT = TOTAL_WIDTH + 200;

    private int carType;
    private int xPos;
    private int yPos;
    private Car inFront;
    private Image image;
    private TrafficController controller;

    public Car(int carType, Car inFront, Image image, TrafficController controller) {
        this.carType = carType;
        this.inFront = inFront;
        this.image = image;
        this.controller = controller;
        if (carType == RED_CAR) {
            xPos = inFront == null ? OUT_RIGHT : Math.min(INIT_X[carType], inFront.getXPos() - 90);
        }
        else {
            xPos = inFront == null ? OUT_LEFT : Math.max(INIT_X[carType], inFront.getXPos() + 90);
        }
        yPos = INIT_Y[carType];
    }

    public void move() {
        int oldXPos = xPos;
        if (carType == RED_CAR) {
            if (inFront.getXPos() - xPos > 100) {
                xPos += 4;
                if (xPos >= BRIDGE_X_LEFT & oldXPos < BRIDGE_X_LEFT)
                    controller.enterOnTheLeft();
                else if (xPos > BRIDGE_X_LEFT && xPos < BRIDGE_X_MID) {
                    if (yPos > BRIDGE_Y) yPos -= 2;
                }
                else if (xPos >= BRIDGE_X_RIGHT_2 && xPos < BRIDGE_X_RIGHT) {
                    if (yPos < INIT_Y[RED_CAR]) yPos += 2;
                }
                else if (xPos >= BRIDGE_X_RIGHT &&  oldXPos < BRIDGE_X_RIGHT)
                    controller.leavingOnTheRight();
            }
        }else {
            if (xPos -inFront.getXPos() > 100) {
                xPos -= 4;
                if (xPos <= BRIDGE_X_RIGHT && oldXPos > BRIDGE_X_RIGHT)
                    controller.enterOnTheRight();
                else if (xPos < BRIDGE_X_RIGHT && xPos > BRIDGE_X_MID) {
                    if (yPos < BRIDGE_Y)
                        yPos += 2;
                }
                else if (xPos <= BRIDGE_X_LEFT_2 && xPos > BRIDGE_X_LEFT) {
                    if(yPos > INIT_Y[BLUE_CAR])
                        yPos -= 2;
                }
                else if (xPos <= BRIDGE_X_LEFT && oldXPos > BRIDGE_X_LEFT)
                    controller.leavingOnTheLeft();
            }
        }
    }

    public void run() {
        boolean outOfSight = carType == RED_CAR ? xPos > TOTAL_WIDTH : xPos < -80;
        while (!outOfSight) {
            move();
            outOfSight = carType == RED_CAR ? xPos > TOTAL_WIDTH : xPos < -80;
            Utils.sleepCurrentThread(30);
        }
        xPos = carType == RED_CAR ? OUT_RIGHT : OUT_LEFT;
    }

    public int getXPos() {
        return xPos;
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, xPos, yPos,null);
    }
}
