package com.dap.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Jobs {

    @Autowired
    private TestService testService;

    /**
     * 表示方法执行完成完后5秒
     *
     * @throws InterruptedException
     */
    @Scheduled(fixedDelay = 5000)
    public void fixedDelayJob() throws InterruptedException {
        System.out.println("fixedDelay 每隔5秒 " + new Date());
        testService.testFunc();
    }

    /**
     * 表示每隔3秒
     */
    @Scheduled(fixedRate = 3000)
    public void fixedRateJob() {
//        System.out.println("fixedRateJob 每隔3秒 " + new Date());
    }

    /**
     * 表示每隔5秒
     */
    @Scheduled(cron = "0/5 * * * * ? ")
    public void cronJob() {
//        System.out.println(new Date() + "...>>cron...");
    }
}
