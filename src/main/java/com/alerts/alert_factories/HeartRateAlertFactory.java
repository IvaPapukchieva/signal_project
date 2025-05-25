package com.alerts.alert_factories;

import com.alerts.Alert;

/**
 * Factory class for creating heart rate related alerts.
 * Produces Alert instances with a message indicating heart rate conditions.
 */
public class HeartRateAlertFactory implements AlertFactory {

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "Heart Rate Alert: " + condition, timestamp);
    }
}