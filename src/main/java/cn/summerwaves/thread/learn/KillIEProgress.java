package cn.summerwaves.thread.learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class KillIEProgress {
    private static final Logger log = getLogger(KillIEProgress.class);


    public void killProcess(){
        Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            rt.exec("taskkill /F /IM iexplore.exe");
            log.info("理论上关闭了浏览器");
        } catch (IOException e) {
            log.info("关闭浏览器失败");
            e.printStackTrace();
        }
    }
}
