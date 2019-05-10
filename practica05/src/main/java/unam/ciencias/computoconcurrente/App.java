package unam.ciencias.computoconcurrente;

public class App {

    public static void main(String[] a) {
        final ProgramGUI window = new ProgramGUI();
        window.pack();
        window.setVisible(true);
        new Thread(() -> {
            while (true) {
                Utils.sleepCurrentThread(25);
                window.repaint();
            }
        }).start();
    }
}
