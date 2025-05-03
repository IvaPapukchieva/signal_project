package com.alerts.alert_decorators;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    public String getAlertDetails() {
        return super.getAlertDetails() +
                "\nRepetition interval: " + interval + " seconds" +
                "\nRepetitions: " + (int) amountOfRepetitions;
    }

}

