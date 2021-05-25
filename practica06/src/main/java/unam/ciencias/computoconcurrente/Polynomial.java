package unam.ciencias.computoconcurrente;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sun.net.www.content.text.plain;

public class Polynomial {
    int[] coefficients; // possibly shared by several polynomials int first; // index of my constant coefficient
    int degree; // number of coefficients that are mine
    int first; // index of my constant coefficient
    static ExecutorService sumexec = Executors.newCachedThreadPool();
    static ExecutorService prodexec = Executors.newCachedThreadPool();

    public Polynomial(int d) {
        coefficients = new int[d];
        degree = d;
        first = 0;
    }

    private Polynomial(int[] myCoefficients, int myFirst, int myDegree) {
        coefficients = myCoefficients;
        first = myFirst;
        degree = myDegree;
    }

    public int get(int index) {
        return coefficients[first + index];
    }

    public void set(int index, int value) {
        coefficients[first + index] = value;
    }

    public int getDegree() {
        return degree;
    }

    public String toString(){
        String ret = "";
        for(int i = first; i < degree; i++){
            if (i == 0){
                ret += coefficients[i] + " + "; 
            } else if (i != degree-1){
                ret += coefficients[i] + " x^" + i + " + "; 
            }
            else {
                ret += coefficients[i] + " x^" + i;
            }
        }
        return ret;
    }

    public Polynomial[] split() {
        Polynomial[] result = new Polynomial[2];
        int newDegree = (int)Math.floor(degree / 2);
        result[0] = new Polynomial(coefficients, first, newDegree);
        result[1] = new Polynomial(coefficients, first + newDegree, newDegree); 
        return result;
    }

    public static Polynomial PolynomialSum(Polynomial p, Polynomial q){
        try {
            int n = p.degree;
            Polynomial r = new Polynomial(n);
            Future<?> future = sumexec.submit(new sumTask(p,q,r));
            future.get();
            return r;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally{
            sumexec.shutdown();
        }
    }

    static class sumTask implements Runnable {
        Polynomial p, q, r;
        public sumTask(Polynomial p, Polynomial q, Polynomial r) {
            this.p = p;
            this.q = q;
            this.r = r;
        }

        public void run() {
            try {
                int n = p.degree;
                if (n == 1) {
                    r.set(0, p.get(0) + q.get(0));
                } else {
                    Polynomial[] pp = p.split(), qq = q.split(), rr = r.split();
                    Future<?>[] future = (Future<?>[]) new Future[2];
                    future[0] = sumexec.submit(new sumTask(pp[0],qq[0],rr[0]));
                    future[1] = sumexec.submit(new sumTask(pp[1],qq[1],rr[1]));
                    future[0].get();
                    future[1].get();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Polynomial PolynomialProd(Polynomial p, Polynomial q){
        try {
            int n = p.degree;
            Polynomial r = new Polynomial(n*n);
            Future<?> future = prodexec.submit(new prodTask(p,q,r));
            future.get();
            return r;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally{
            prodexec.shutdown();
        }
    }

    static class prodTask implements Runnable {
        Polynomial p, q, r;
        public prodTask(Polynomial p, Polynomial q, Polynomial r) {
            this.p = p;
            this.q = q;
            this.r = r;
        }

        public void run() {
            try {
                int n = r.degree;
                if (n == 1){
                    r.set(0,(p.get(0) * q.get(0)));
                }
                else {
                    Polynomial[] pp = p.split(), qq = q.split(), rr = r.split();
                    Future<?>[] future = (Future<?>[]) new Future[4];
                    future[0] = prodexec.submit(new prodTask(pp[0],qq[0],rr[0]));
                    future[0].get();
                    future[1] = prodexec.submit(new prodTask(pp[1],qq[1],rr[1]));
                    future[1].get();
                    future[2] = prodexec.submit(new prodTask(pp[1],qq[0],rr[0]));
                    future[2].get();
                    future[3] = prodexec.submit(new prodTask(pp[0],qq[1],rr[1]));
                    future[3].get();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
