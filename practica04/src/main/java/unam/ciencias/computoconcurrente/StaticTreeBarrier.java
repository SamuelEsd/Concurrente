package unam.ciencias.computoconcurrente;

import java.util.concurrent.atomic.AtomicInteger;

public class StaticTreeBarrier implements Barrier {
    int radix;
    boolean sense;
    Node[] node;
    ThreadLocal<Boolean> threadSense;
    int nodes;

    public StaticTreeBarrier(int size, int radix) {
        this.radix = radix;
        nodes = 0;
        node = new Node[size];
        int depth = 0;
        while(size > 1){
            depth++;
            size = size / radix;
        }
        build(null, depth);
        sense = false;
        threadSense = new ThreadLocal<Boolean>() {
            protected Boolean initialValue() { return !sense;};
        };
    }

    void build(Node parent, int depth){
        if (depth == 0){
            if (nodes < node.length){
                node[nodes++] = new Node(parent, 0);
                parent.children++;
                parent.childCount.incrementAndGet();
            }
        } else {
            Node myNode = new Node(parent, 0);
            if (parent != null){
                parent.children++;
                parent.childCount.incrementAndGet();
            }
            node[nodes++] = myNode;
            for (int i = 0; i < radix; i++){
                build(myNode, depth - 1);
            }
        }
    }

    @Override
    public void await() {
        node[Integer.parseInt(Thread.currentThread().getName())].await();
    }

    private class Node {
        AtomicInteger childCount;
        Node parent;
        int children;

        public Node(Node myParent, int count){
            children = count;
            childCount = new AtomicInteger(count);
            parent = myParent;
        }

        public void await(){
            boolean mySense = threadSense.get();
            while (childCount.get() > 0){
                try {
                    Thread.sleep(50);    
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                
                System.out.print("El while de child mayor a 0: ");
                System.out.println(Thread.currentThread().getName());
            }
            this.childCount.set(this.children);
            if (parent != null) {
                parent.childDone();
                while(sense != mySense) {
                    try {
                        Thread.sleep(50);    
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.print("El while de not root: ");
                    System.out.println(Thread.currentThread().getName());
                }
            } else {
                sense = !sense;
            }
            threadSense.set(!mySense);
        }

        public void childDone() {
            childCount.getAndDecrement();
        }
    }
}
