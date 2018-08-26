package cn.summerwaves.thread;

import org.slf4j.Logger;

import java.util.concurrent.Callable;

import static org.slf4j.LoggerFactory.getLogger;

public class Worker implements Callable {
    private static final Logger log = getLogger(WorkThread.class);

    @Override
    public Object call(){
        Thread.currentThread().setName("工作者线程");
        while (true) {
            log.info("{} 运行中………………",Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
