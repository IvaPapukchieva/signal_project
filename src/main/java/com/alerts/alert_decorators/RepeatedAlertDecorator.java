package com.alerts.alert_decorators;

import com.alerts.Alert;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
//This class needs to be implemented

public class RepeatedAlertDecorator extends AlertDecorator{

    private final double interval;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public RepeatedAlertDecorator(Alert decoratedAlert, double interval) {
        super(decoratedAlert);
        this.interval=interval;
    }

    public void checkAlertForTime() {
        scheduler.scheduleAtFixedRate(this::triggerAlert, 0, (long) interval, TimeUnit.SECONDS);
    }


    @Override
    public void triggerAlert() {
        super.triggerAlert();

    }
}
