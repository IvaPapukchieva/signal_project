package com.alerts.alert_decorators;

/**
 * Interface representing a basic alert component in the healthcare monitoring system.
 * Used by all alert types (e.g., blood pressure, ECG, oxygen saturation) to define
 * common behavior and structure for triggered alerts.
 */
public interface AlertComponent {

    /**
     * @return the ID of the patient associated with the alert.
     */
    String getPatientId();

    /**
     * @return the condition or measurement type that triggered the alert (e.g., "ECG", "SystolicPressure").
     */
    String getCondition();

    /**
     * @return the timestamp of when the alert condition was detected.
     */
    long getTimestamp();

    /**
     * Triggers the alert, typically by logging or notifying external systems.
     */
    void triggerAlert();

    /**
     * @return a human-readable description of the alert.
     */
    @Override
    String toString();
}
