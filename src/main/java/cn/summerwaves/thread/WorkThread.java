package cn.summerwaves.thread;

import cn.summerwaves.thread.learn.KillIEProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.slf4j.LoggerFactory.getLogger;

public class WorkThread {
    private static final Logger log = getLogger(WorkThread.class);
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Worker worker = new Worker();
        Future<Integer> future = executorService.submit(worker);

        log.info("工作线程已启动");

        TimeoutKiller timeoutKiller = new TimeoutKiller();
        timeoutKiller.setFuture(future);
        Thread timeoutKillerThread = new Thread(timeoutKiller);
        timeoutKillerThread.setName("超时关闭线程");
        timeoutKillerThread.start();
        log.info("超时关闭线程已启动");

        Monitor monitor = new Monitor();
        monitor.setFuture(future);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        log.info("监视线程已启动");
    }

}








