package morgan_stanley;

public class PrintPattern1 {
    private static final Object lock = new Object();
    private static boolean numberTurn = true;

    public static void main(String[] args) {
        Thread t1 = new Thread( () -> {
            for(int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (!numberTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println(i);
                    numberTurn = false;
                    lock.notifyAll();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for(char c = 'A'; c <= 'E'; c++) {
                synchronized (lock) {
                    while (numberTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println(c);
                    numberTurn = true;
                    lock.notifyAll();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
