package com.haiyan.deflower.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author haiyan
 **/
@Slf4j
@Component
@Order(1)
public class PushAlarm implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        log.info("------------->" + "项目启动，now =" + new Date());
        this.myTimer();
    }

    public void myTimer() {

        // userId 为空时，会推送给连接此 WebSocket 的所有人
        String userId = null;
        Runnable runnable = () -> {
            String message = "尊敬的的用户你好"; // 第三方接口返回数据
            try {
                WebSocketServer.sendInfo(message, userId); // 推送
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        
    }

}
