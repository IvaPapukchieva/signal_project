package com.alerts.alert_factories;

import com.alerts.Alert;

/**
 * Factory interface for creating Alert instances in the healthcare monitoring system.
 * Implementations of this interface are responsible for producing specific types of alerts
 * (e.g., HeartRateAlert, BloodPressureAlert) based on the provided patient data.
 */
public interface AlertFactory {

    /**
     * Creates a new Alert based on the given patient information and condition.
     *
     * @param patientId  the unique identifier of the patient
     * @param condition  the condition or measurement type triggering the alert (e.g., "HeartRate", "ECG")
     * @param timestamp  the time at which the alert condition was detected
     * @return a new Alert instance corresponding to the provided condition
     */
    Alert createAlert(String patientId, String condition, long timestamp);
}



