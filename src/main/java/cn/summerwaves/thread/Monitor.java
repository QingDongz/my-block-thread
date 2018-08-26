package cn.summerwaves.thread;

import org.slf4j.Logger;

import java.util.concurrent.Future;

import static org.slf4j.LoggerFactory.getLogger;

public class Monitor implements Runnable {
    private static final Logger log = getLogger(Monitor.class);
    private Future future;

    public void setFuture(Future future) {
        this.future = future;
    }


    @Override
    public void run() {
        while (true) {
            log.info("工作者线程的状态为中断状态:{}", future.isCancelled());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
