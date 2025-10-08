package morgan_stanley;

import java.util.concurrent.locks.LockSupport;

public class PrintPattern5 {

    static Thread t1, t2;

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            for (int i = 1; i < 6; i++) {
                System.out.println(i);
                LockSupport.unpark(t2); // wake t2
                LockSupport.park();     // block t1
            }
        });

        t2 = new Thread(() -> {
            for(char c = 'A'; c <= 'E'; c++) {
                LockSupport.park();     // block until unparked
                System.out.println(c);
                LockSupport.unpark(t1); // wake t1
            }
        });

        t1.start();
        t2.start();
    }
}
