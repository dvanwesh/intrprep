package threads;

class IntCounter implements Runnable {
    ThreadLocal<Integer> counter = new ThreadLocal<>();
    @Override
    public void run() {
        counter.set(1);
        counter.set(counter.get().intValue() + 1);
        System.out.println(counter.get().intValue());
    }
}
public class ThreadLocalDemo {
    public static void main(String[] args) {
        IntCounter intCounter = new IntCounter();
        Thread t1 = new Thread(intCounter);
        Thread t2 = new Thread(intCounter);
        Thread t3 = new Thread(intCounter);
        Thread t4 = new Thread(intCounter);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // wait for threads to end
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (Exception e) {
            System.out.println("Interrupted");
        }
    }

}
