package datastructures.util.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FactorialSquareCalculator extends RecursiveTask<Integer> {
    private final Integer n;

    public FactorialSquareCalculator(Integer n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return 1;
        }
        FactorialSquareCalculator calc = new FactorialSquareCalculator(n - 1);
        calc.fork();
        return n * n + calc.join();
    }

    public static void main(String[] arg) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialSquareCalculator calc = new FactorialSquareCalculator(4);
        System.out.println(calc.compute());
        forkJoinPool.execute(calc);
        System.out.println(forkJoinPool.toString());
    }
}
