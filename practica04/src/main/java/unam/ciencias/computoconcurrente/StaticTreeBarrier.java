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
        for(int i = 0; i < node.length; i++){
            Node myNode;
            if(i > 0){
                myNode = new Node(node[(i-1)/2], 0);
            }
            else{
                myNode = new Node(null, 0);
            }
            if((i*2)+1 < node.length){
                myNode.children++;
                myNode.childCount.incrementAndGet();
            }
            if((i*2)+2 < node.length){
                myNode.children++;
                myNode.childCount.incrementAndGet();
            }
            node[i] = myNode;
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
                    Thread.sleep(1);    
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
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
