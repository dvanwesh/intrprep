package morgan_stanley;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintPattern2 {
    private static final Lock lock = new ReentrantLock();
    private static final Condition numTurn = lock.newCondition();
    private static final Condition charTurn = lock.newCondition();

    private static boolean isNum = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for(int i = 1; i < 6; i++) {
                lock.lock();
                try {
                    while (!isNum) {
                        numTurn.await();
                    }
                    System.out.print(i);
                    isNum = false;
                    charTurn.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for(char c = 'A'; c <= 'E'; c++) {
                lock.lock();
                try {
                    while (isNum) {
                        charTurn.await();
                    }
                    System.out.print(c);
                    isNum = true;
                    numTurn.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });

        t1.start();
        t2.start();
    }

}
