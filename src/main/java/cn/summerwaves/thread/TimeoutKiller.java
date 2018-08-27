package cn.summerwaves.thread;

import cn.summerwaves.thread.learn.KillIEProgress;
import org.slf4j.Logger;

import java.util.concurrent.Future;

import static org.slf4j.LoggerFactory.getLogger;

public class TimeoutKiller implements Runnable {
    private static final Logger log = getLogger(TimeoutKiller.class);
    private long timeout = 5 * 60 * 1000;
    private Long threadStartTime;
    private Future future;

    public void setFuture(Future future) {
        this.future = future;
    }

    @Override
    public void run() {
        if (threadStartTime == null) {
            threadStartTime = System.currentTimeMillis();
        }
        while (System.currentTimeMillis() < (threadStartTime + timeout)) {
            log.info("{} 等待中，距离关闭工作者线程剩余 {} 秒", Thread.currentThread().getName(), ((threadStartTime + timeout) - System.currentTimeMillis()) / 1000);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        KillIEProgress killIEProgress = new KillIEProgress();
        killIEProgress.killProcess();

        WorkThread.isContinue = false;
    }
}
