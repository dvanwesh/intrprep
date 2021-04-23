package leetcode.snap;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;

public class TaskScheduler implements Callable<String> {
    Queue<Solution.Task> tasks = new PriorityQueue<>((a, b) -> a.time - b.time <= 0 ? -1 : 1);

    public String executeTask(){
        if(!tasks.isEmpty() && tasks.peek().time <= System.currentTimeMillis()){
            return tasks.poll().taskName;
        }
        return "no task to execute";
    }
    public void queueTask(long time, String task){
        tasks.offer(new Solution.Task(time, task));
    }

    @Override
    public String call() throws Exception {
        String task = executeTask();
        if(tasks.isEmpty()){
            Thread.currentThread().interrupt();
        }
        return task;
    }
}
