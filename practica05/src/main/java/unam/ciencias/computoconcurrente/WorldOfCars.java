package unam.ciencias.computoconcurrente;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class WorldOfCars extends JPanel {
    private Image bridge;
    private Image redCar;
    private Image blueCar;
    private TrafficController controller;
    private ArrayList<Car> blueCars;
    private ArrayList<Car> redCars;

    public WorldOfCars() {
        blueCars = new ArrayList<>();
        redCars = new ArrayList<>();
        controller = new TrafficController();
        MediaTracker mediaTracker = new MediaTracker(this);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        redCar = toolkit.getImage(getClass().getClassLoader().getResource("redcar.gif"));
        mediaTracker.addImage(redCar, 0);
        blueCar = toolkit.getImage(getClass().getClassLoader().getResource("bluecar.gif"));
        mediaTracker.addImage(blueCar, 1);
        bridge = toolkit.getImage(getClass().getClassLoader().getResource("bridge.gif"));
        mediaTracker.addImage(bridge, 2);
        try {
            mediaTracker.waitForID(0);
            mediaTracker.waitForID(1);
            mediaTracker.waitForID(2);
        } catch (java.lang.InterruptedException e) {
            System.out.println("No se pudo cargar una imagen");
        }
        redCars.add(new Car(Car.RED_CAR,null, redCar, null));
        blueCars.add(new Car(Car.BLUE_CAR,null, blueCar, null));
        setPreferredSize(new Dimension(bridge.getWidth(null),bridge.getHeight(null)));
    }

    public void paintComponent(Graphics g) {
        g.drawImage(bridge,0,0,this);
        for (Car c : redCars)
            c.draw(g);
        for (Car c : blueCars)
            c.draw(g);
    }

    public void addCar(final int carType) {
        SwingUtilities.invokeLater(() -> {
            Car c;
            if (carType == Car.RED_CAR) {
                c = new Car(carType, redCars.get(redCars.size()-1), redCar, controller);
                redCars.add(c);
            }else {
                c = new Car(carType, blueCars.get(blueCars.size()-1), blueCar, controller);
                blueCars.add(c);
            }
            new Thread(c).start();
        });
    }
}