package com.alerts.alert_decorators;


import java.util.List;

public class AlertDecorator implements AlertComponent{
    private final AlertComponent decoratedAlert;;

    public AlertDecorator(AlertComponent decoratedAlert) {
        this.decoratedAlert = decoratedAlert;

    }
    @Override
    public String getPatientId() {
        return decoratedAlert.getPatientId();
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }

    @Override
    public void triggerAlert() {
        decoratedAlert.triggerAlert();
    }

    @Override
    public String toString() {
        return decoratedAlert.toString();
    }

}
