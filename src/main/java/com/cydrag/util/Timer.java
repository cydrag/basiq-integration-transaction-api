package com.cydrag.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Timer {

    private static final Logger log = LoggerFactory.getLogger(Timer.class);

    private Timer() {}

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (InterruptedException e) {
                log.error("Error executing function in the background - thread unexpectedly interrupted.", e);
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
