package unam.ciencias.computoconcurrente;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class App {
    
    private static volatile Semaphore mutex = new Semaphore(1);
    public static void main(String [] args) throws InterruptedException {
        if(args.length == 0) {
            System.err.println("NO OPTION WAS PROVIDED");
            System.exit(1);
        }

        switch (args[0]) {
            case "JScreen":
                example1();
                break;
            case "JScreen2":
                example2();
                break;
            case "Exercise":
                exercise();
                break;
            default:
                System.err.printf("INVALID OPTION %s. VALID OPTIONS ARE [JScree, JScreen2, Exercise]", args[0]);
                System.exit(1);
        }
    }

    private static void example1() throws InterruptedException {
        ScreenHW screen = new JScreen();
        screen.write('A', 5,2);
        screen.write('B', 8,11);
        Thread.sleep(3000);
        screen.write(' ', 5,2);
    }

    private static void example2() throws InterruptedException {
        ScreenHL d = new JScreen2();
        for(int i=0; i < 20; i++) {
            d.addRow("AAAAAAAAAAAA  "+i);
            d.addRow("BBBBBBBBBBBB  "+i);
            Thread.sleep(1000);
            d.deleteRow(0);
            Thread.sleep(1000);
        }
    }

    private static void exercise() throws InterruptedException {
        final ScreenHL d = new JScreen2();
        new Thread (() -> addProc(d)).start();
        new Thread (() -> deleteProc(d)).start();
    }

    private static void addProc(ScreenHL d) {
        Random r  = new Random();
        for(int i=0; i < 30; i++) {
            try {
                mutex.acquire();
                d.addRow("TEXTO RANDOM  "+i);
                
                if (r.nextInt(2) == 1){
                    Thread.sleep(100);        
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
            finally{
                mutex.release();
            }
        }
        // TODO: Add several 'addRow' calls, interleaving some random thread sleeps.
    }

    private static void deleteProc(ScreenHL d) {
        try {
            Thread.sleep(7000); 
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        Random r  = new Random();
        for(int i=0; i < 50; i++) {
            try {
                mutex.acquire();
                d.deleteRow(0);
                if (r.nextInt(2) == 1){
                    System.out.println(r.nextInt(2));
                    Thread.sleep(4000);        
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
            finally{
                mutex.release();
            }
        }
        // TODO: Add several 'deleteRow(0)' calls, interleaving some random thread sleeps
    }

}
