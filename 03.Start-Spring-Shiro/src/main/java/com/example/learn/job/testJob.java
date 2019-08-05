package com.example.learn.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class testJob {
    private final Log logger = LogFactory.getLog(testJob.class);

    @Scheduled(initialDelay = 1000, fixedRate = 10000)
    public void test() {
        logger.info("test job \uD83D\uDC02\uD83D\uDC02\uD83D\uDC02\uD83D\uDC02\uD83D\uDC02\uD83D\uDC02\uD83D\uDC02\uD83D\uDC02");
    }
}
