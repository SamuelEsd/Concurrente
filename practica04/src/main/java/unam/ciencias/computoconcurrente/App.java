package unam.ciencias.computoconcurrente;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Runnable;

public class App {

    public static void main(String[] a) {
        Thread.currentThread().setName("MAIN");
        final BallWorld world = new BallWorld();
        initGraphicalContext(world);
        initBalls(world);
        
        Random randy = new Random();
        Runnable hitman = () ->{
            while (true) {
                int mustIKill = randy.nextInt(100);
                if (mustIKill < 25){
                    world.release();
                }
                else{
                    Utils.sleepCurrentThread(randy.nextInt(1000));
                }
            }
        };

        System.out.println(a.length);
        if (a.length >= 1 && a[0].equals("--enableKiller")){
            hitman.run();
        }
    }

    private static JFrame initGraphicalContext(BallWorld world) {
        JFrame window = new JFrame();
        SwingUtilities.invokeLater(() -> {
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.getContentPane().add(world);
            window.pack();
            window.setVisible(true);
        });
        return window;
    }

    private static void initBalls(BallWorld world){
        List<Ball> balls = Arrays.asList(
                new Ball(world, 50, 80, 5, 10, Color.red),
                new Ball(world, 70, 100, 8, 6, Color.blue),
                new Ball(world, 150, 100, 9, 7, Color.green),
                new Ball(world, 200, 130, 3, 8, Color.black)
        );

        for(Ball b : balls) {
            Utils.sleepCurrentThread((int)(5000 * Math.random()));
            new Thread(b, String.format("Color %s", b.getColor())).start();
        }
    }
}
