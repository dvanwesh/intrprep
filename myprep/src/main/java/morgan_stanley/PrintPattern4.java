package morgan_stanley;

import java.util.concurrent.Semaphore;

public class PrintPattern4 {

    private record Pair(int number, char character) {};
    public static void main(String[] args) {
        Semaphore semNum = new Semaphore(1);
        Semaphore semChar = new Semaphore(0);

        Thread t1 = new Thread(() -> {
            for(int i = 1; i < 6; i++) {
                try {
                    semNum.acquire();
                    System.out.print(i);
                    semChar.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for(char c = 'A'; c <= 'E'; c++) {
                try {
                    semChar.acquire();
                    System.out.print(c);
                    semNum.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
    }
}
