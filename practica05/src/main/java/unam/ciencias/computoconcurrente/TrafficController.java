package unam.ciencias.computoconcurrente;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class TrafficController {
    private AtomicBoolean ocupado = new AtomicBoolean(false);
    public void enterOnTheLeft() {
        synchronized(this){
            if (!ocupado.get()) {
                ocupado.set(true);
            }
            else {
                try {
                    while (ocupado.get()){
                        wait();   
                    }
                    ocupado.set(true);
                } catch (InterruptedException ie){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    public void enterOnTheRight() {
        synchronized(this){
            if (!ocupado.get()) {
                ocupado.set(true);
            }
            else {
                try {
                    while (ocupado.get()){
                        wait();   
                    }
                    ocupado.set(true);
                } catch (InterruptedException ie){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    public void leavingOnTheLeft() {
        synchronized(this){
            ocupado.set(false);
            notify();
        }
    }
    public void leavingOnTheRight() {
        synchronized(this){
            ocupado.set(false);
            notify();
        }
    }
}