package leetcode.snap;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.regex.*;

public class Solution {
    Queue<Task> tasks = new PriorityQueue<>((a, b) -> a.time - b.time <= 0 ? -1 : 1);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Solution sol = new Solution();
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.queueTask(System.currentTimeMillis()+10, "task10");
        scheduler.queueTask(System.currentTimeMillis()+11, "task11");
        scheduler.queueTask(System.currentTimeMillis()+14, "task14");
        scheduler.queueTask(System.currentTimeMillis()+13, "task13");
        scheduler.queueTask(System.currentTimeMillis()+12, "task12");

        scheduler.queueTask(System.currentTimeMillis()+4, "task5");
        scheduler.queueTask(System.currentTimeMillis()+1, "task1");
        scheduler.queueTask(System.currentTimeMillis()+1, "task2");
        scheduler.queueTask(System.currentTimeMillis()+1, "task3");
        scheduler.queueTask(System.currentTimeMillis()+3, "task4");
        ThreadFactory factory = new BasicThreadFactory.Builder()
            .namingPattern("name")
            .build();
        ExecutorService service = Executors.newFixedThreadPool(3, factory);
        Future<String> res = service.submit(scheduler);
        System.out.println(res.get());
    }

    public void queueTask(long time, String task){
        tasks.offer(new Task(time, task));
    }

    public String executeTask(){
        if(!tasks.isEmpty() && tasks.peek().time <= System.currentTimeMillis()){
            return tasks.poll().taskName;
        }
        return "";
    }

    public Queue<Task> getTasks(){
        return tasks;
    }

    static class Task{
        long time;
        String taskName;
        public Task(long t, String name){
            time = t;
            taskName = name;
        }
    }
}


