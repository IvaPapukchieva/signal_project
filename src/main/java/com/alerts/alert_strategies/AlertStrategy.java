package com.alerts.alert_strategies;

import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_decorators.AlertExecutor;
import com.data_management.PatientRecord;

import java.util.List;
import java.util.List;

/**
 * Strategy interface for evaluating patient records and generating alerts.
 * Implementations of this interface define specific alert logic
 * for different conditions (e.g., heart rate, oxygen saturation, blood pressure).
 */
public interface AlertStrategy {

    /**
     * Analyzes the provided patient records and determines whether any alerts should be triggered.
     *
     * @param patientId the ID of the patient whose records are being evaluated
     * @param records   a list of recent PatientRecord instances for that patient
     */
    void checkAlert(String patientId, List<PatientRecord> records);

    /**
     * @return a list of triggered AlertComponents based on the evaluation.
     */
    List<AlertComponent> getTriggeredAlerts();
}