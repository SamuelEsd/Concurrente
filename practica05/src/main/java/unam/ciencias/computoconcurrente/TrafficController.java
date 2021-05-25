package unam.ciencias.computoconcurrente;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;



// EJERCICIO 1
// // Descomentar para que jale
// public class TrafficController {
//     private AtomicBoolean ocupado = new AtomicBoolean(false);
//     public void enterOnTheLeft() {
//         synchronized(this){
//             if (!ocupado.get()) {
//                 ocupado.set(true);
//             }
//             else {
//                 try {
//                     while (ocupado.get()){
//                         wait();   
//                     }
//                     ocupado.set(true);
//                 } catch (InterruptedException ie){
//                     Thread.currentThread().interrupt();
//                 }
//             }
//         }
//     }
//     public void enterOnTheRight() {
//         synchronized(this){
//             if (!ocupado.get()) {
//                 ocupado.set(true);
//             }
//             else {
//                 try {
//                     while (ocupado.get()){
//                         wait();   
//                     }
//                     ocupado.set(true);
//                 } catch (InterruptedException ie){
//                     Thread.currentThread().interrupt();
//                 }
//             }
//         }
//     }
//     public void leavingOnTheLeft() {
//         synchronized(this){
//             ocupado.set(false);
//             notify();
//         }
//     }
//     public void leavingOnTheRight() {
//         synchronized(this){
//             ocupado.set(false);
//             notify();
//         }
//     }
// }


// ACTIVIDAD 2.1 Prioridad a los carros pasando en el momento
// public class TrafficController {
//     private AtomicInteger rojos= new AtomicInteger(0);
//     private AtomicInteger azules= new AtomicInteger(0);

//     // rojo
//     public void enterOnTheLeft() {
//         synchronized(this){
//             if (azules.get() == 0) {
//                 rojos.incrementAndGet();
//             }
//             else {
//                 try {
//                     while (azules.get() > 0){
//                         wait();   
//                     }
//                     rojos.incrementAndGet();
//                 } catch (InterruptedException ie){
//                     Thread.currentThread().interrupt();
//                 }
//             }
//         }
//     }
//     public void enterOnTheRight() {
//         synchronized(this){
//             if (rojos.get() == 0) {
//                 azules.incrementAndGet();
//             }
//             else {
//                 try {
//                     while (rojos.get() > 0){
//                         wait();   
//                     }
//                     azules.incrementAndGet();
//                 } catch (InterruptedException ie){
//                     Thread.currentThread().interrupt();
//                 }
//             }
//         }
//     }
//     public void leavingOnTheLeft() {
//         synchronized(this){
//             azules.getAndDecrement();
//             notifyAll();
//         }
//     }
//     // rojo
//     public void leavingOnTheRight() {
//         synchronized(this){
//             rojos.getAndDecrement();
//             notifyAll();
//         }
//     }
// }


// ACTIVIDAD 2.2 Prioridad a los carros pasando en el momento
// public class TrafficController {
//     private AtomicInteger rojos= new AtomicInteger(0);
//     private AtomicInteger azules= new AtomicInteger(0);
//     private AtomicBoolean tocaRojo = new AtomicBoolean(false);

//     // rojo
//     public void enterOnTheLeft() {
//         synchronized(this){
//             try {
//                 while (!tocaRojo.get() || rojos.get() != 0){
//                     wait();   
//                 }
//                 rojos.incrementAndGet();
//             } catch (InterruptedException ie){
//                 Thread.currentThread().interrupt();
//             }
//         }
//     }
//     public void enterOnTheRight() {
//         synchronized(this){
//             try {
//                 while (tocaRojo.get() || azules.get() != 0){
//                     wait();   
//                 }
//                 azules.incrementAndGet();
//             } catch (InterruptedException ie){
//                 Thread.currentThread().interrupt();
//             }
//         }
//     }
//     public void leavingOnTheLeft() {
//         synchronized(this){
//             tocaRojo.set(!tocaRojo.get());
//             if (rojos.get() == 1){
//                 rojos.decrementAndGet();
//             }
//             notifyAll();
//         }
//     }
//     // rojo
//     public void leavingOnTheRight() {
//         synchronized(this){
//             tocaRojo.set(!tocaRojo.get());
//             if (azules.get() == 1){
//                 azules.decrementAndGet();
//             }
//             notifyAll();
//         }
//     }
// }

