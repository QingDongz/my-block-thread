package cn.summerwaves.thread.learn;

import cn.summerwaves.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimeoutWaitWithCondition {
    private static final Logger logger = LoggerFactory.getLogger(TimeoutWaitWithCondition.class);
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean ready = false;
    protected static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        ready = random.nextInt(100) < 5 ? true : false;
                        if (ready) {
                            condition.signal();
                        }
                    }finally {
                        lock.unlock();
                    }
                    Tools.randomPause(500);
                }
            }
        };

        thread.setDaemon(true);
        thread.start();

        waiter(1000);
    }

    public static void waiter(final long timeOut) throws InterruptedException {
        if (timeOut < 0) {
            throw new IllegalArgumentException();
        }
        final Date deadline = new Date(System.currentTimeMillis() + timeOut);

        boolean continueToWait = true;
        lock.lock();
        try {
            while (!ready) {
                logger.info("还未准备完毕，继续等待中:{}", continueToWait);
                if (!continueToWait) {
                    logger.error("等待超时，不能继续执行目标");
                    return;
                }
                continueToWait = condition.awaitUntil(deadline);
            }
            guarededAction();
        }finally {
            lock.unlock();
        }
    }

    private static void guarededAction() {
        logger.info("执行目标……");
    }



}
