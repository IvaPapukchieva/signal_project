package com.alerts.alert_decorators;


import java.util.List;

/**
 * A base class for decorating AlertComponent instances.
 * This allows extending alert behavior dynamically using the Decorator pattern.
 */
public class AlertDecorator implements AlertComponent {

    private final AlertComponent decoratedAlert;

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