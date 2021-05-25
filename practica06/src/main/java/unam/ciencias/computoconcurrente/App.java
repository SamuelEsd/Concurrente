package unam.ciencias.computoconcurrente;

public class App {

    public static void main(String[] a) {
        System.out.println("Original");
        int[] arrayTest = {3,89, 90, 7, 9, 12321, 3424,512, -123, -3,-234 ,23, 6, 12, 43, 243, 8};
        for(int i : arrayTest){
            System.out.print(i+" ");
        } 
        System.out.println();

        System.out.println("Sorted");
        Sorting.mergeSort(arrayTest);
        for(int i : arrayTest){
            System.out.print(i+" ");
        } 
        System.out.println("\n\n");

        Polynomial pe = new Polynomial(4);
        Polynomial cu = new Polynomial(4);
        for (int i = 0; i < 4; i++){
            pe.set(i, i+1);
            cu.set(i, 3-i);
        }
        System.out.println(pe);
        System.out.println(cu);
        Polynomial erre = Polynomial.PolynomialSum(pe, cu);
        System.out.println(erre);
        System.out.println("\n\n");

        Polynomial pe2 = new Polynomial(2);
        Polynomial cu2 = new Polynomial(2);
        for (int i = 0; i < 2; i++){
            pe2.set(i, 2);
            cu2.set(i, 2);
        }
        System.out.println(pe2);
        System.out.println(cu2);
        Polynomial erre2 = Polynomial.PolynomialProd(pe2, cu2);
        System.out.println(erre2);
    }
}