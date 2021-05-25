package unam.ciencias.computoconcurrente;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Sorting {
    static ExecutorService exec = Executors.newCachedThreadPool();

    public static void mergeSort(int[] array) {
        try {
            Future<?> future = exec.submit(new AddTask(array,0,array.length-1));
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        exec.shutdown();
    }

    static class AddTask implements Runnable {
        int[] array;
        int a, b;
        public AddTask(int[] array,int a,int b){
            this.array = array;
            this.a = a;
            this.b = b;
        }
        public void run() {
            try {
                //System.out.println("llamada número: "+a+" , "+b);
                int n = (b-a)+1;
                // If not the base case
                if (n > 1) {
                    // Recursive call
                    int half = a+(int)(Math.floor(n/2));
                    //System.out.println("the half is: "+half);
                    Future<?>[] futures = (Future<?>[]) new Future[2];
                    futures[0] = exec.submit(new AddTask(array,a,half-1));
                    futures[0].get();
                    futures[1] = exec.submit(new AddTask(array,half,b));
                    futures[1].get();

                    // Merge part
                    int[] temp = new int[n];
                    int l = a;
                    int r = half;
                    for(int i = 0; i < n; i++){
                        if (l == half){
                            temp[i] = array[r++];
                        }
                        else if (r == b+1) {
                            temp[i] = array[l++];
                        }
                        else if (array[l] < array[r]) {
                            temp[i] = array[l++];
                        }
                        else {
                            temp[i] = array[r++];
                        }
                    } 
                    for (int i = 0; i < n; i++){
                        array[a+i] = temp[i];
                    }
                }
                //System.out.print("Sorted: ");
                // for(int i = 0;i < n; i++){
                //     System.out.print(array[i+a]+", ");
                // }
                //System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
