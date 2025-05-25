package com.alerts.alert_decorators;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A decorator that repeatedly triggers an alert at fixed intervals.
 * Useful for reminding about critical alerts until acknowledged or resolved.
 */
public class RepeatedAlertDecorator extends AlertDecorator {

    private final long interval;
    private final double amountOfRepetitions;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public RepeatedAlertDecorator(AlertComponent decoratedAlert, long interval, double amountOfRepetitions) {
        super(decoratedAlert);
        this.interval = interval;
        this.amountOfRepetitions = amountOfRepetitions;
    }

    @Override
    public void triggerAlert() {
        final int[] currentRepetitions = {0};
        scheduler.scheduleAtFixedRate(() -> {
            if (currentRepetitions[0] >= amountOfRepetitions) {
                scheduler.shutdown();
                return;
            }

            super.triggerAlert();
            System.out.println("This is repetition #" + (currentRepetitions[0] + 1));
            currentRepetitions[0]++;
        }, 0, (long) interval, TimeUnit.SECONDS);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nRepetition interval: " + interval + " seconds" +
                "\nRepetitions: " + (int) amountOfRepetitions;
    }
}
