package cn.summerwaves.thread;

import org.slf4j.Logger;

import java.util.concurrent.Callable;

import static org.slf4j.LoggerFactory.getLogger;

public class Worker implements Callable {
    private static final Logger log = getLogger(WorkThread.class);

    @Override
    public Object call() throws InterruptedException {
        Thread.currentThread().setName("工作者线程");
        log.info("工作者线程执行1次");
        Thread.sleep(3000);
        log.info("工作者线程执行2次");
        Thread.sleep(3000);
        log.info("工作者线程执行3次");
        Thread.sleep(3000);
        log.info("工作者线程执行4次");
        Thread.sleep(3000);
        log.info("工作者线程执行5次");
        Thread.sleep(3000);
        log.info("工作者线程执行6次");
        Thread.sleep(3000);
        return "test";
    }
}
