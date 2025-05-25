package com.alerts.alert_factories;

import com.alerts.Alert;

/**
 * Factory for creating blood oxygen-related alerts.
 * Implements the AlertFactory interface to generate Alert instances
 * with predefined blood oxygen alert messages.
 */
public class BloodOxygenAlertFactory implements AlertFactory {

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "Blood Oxygen Alert: " + condition, timestamp);
    }
}