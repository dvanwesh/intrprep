package morgan_stanley;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PrintPattern3 {
    public static void main(String[] args) {
        BlockingQueue<Character> charQ = new ArrayBlockingQueue<>(1);
        BlockingQueue<Integer> numQ = new ArrayBlockingQueue<>(1);

        Thread t1 = new Thread(() -> {
            for(int i = 1; i < 6; i++) {
                try {
                    System.out.println(i);
                    charQ.put((char) ('A' + i - 1)); // pass turn
                    numQ.take(); // wait for ack
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 1; i < 6; i++) {
                try {
                    char c = charQ.take();
                    System.out.println(c);
                    numQ.put(1); // ack back
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
    }
}
