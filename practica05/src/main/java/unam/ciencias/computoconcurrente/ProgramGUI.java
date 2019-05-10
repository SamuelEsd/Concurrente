package unam.ciencias.computoconcurrente;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ProgramGUI extends JFrame {
    WorldOfCars display;
    JButton addLeft;
    JButton addRight;

    public ProgramGUI() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        display = new WorldOfCars();
        c.add("Center",display);
        addLeft = new JButton("Add left");
        addRight = new JButton("Add right");
        addLeft.addActionListener((e) -> { display.addCar(Car.RED_CAR); });
        addRight.addActionListener((e) -> { display.addCar(Car.BLUE_CAR); });

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(addLeft);
        p1.add(addRight);
        c.add("South",p1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }   
}