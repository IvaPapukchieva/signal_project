package com.alerts.alert_strategies;

import com.alerts.Alert;
import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_decorators.AlertExecutor;
import com.data_management.PatientRecord;

import java.util.List;

public class HypotensiveHypoxemiaStrategy implements AlertStrategy {

    private static final double SYSTOLIC_THRESHOLD = 90;
    private static final double OXYGEN_SATURATION_THRESHOLD = 92;

    @Override
    public void checkAlert(String patientId, List<PatientRecord> records) {
        clearAlerts();
        double systolic = -1;
        double oxygenSaturation = -1;

        for (PatientRecord record : records) {
            String type = record.getRecordType();
            if (type.equals("Systolic")) {
                systolic = record.getMeasurementValue();
            }

            if (type.equals("Oxygen Saturation")) {
                oxygenSaturation = record.getMeasurementValue();
            }

            if (systolic < SYSTOLIC_THRESHOLD && oxygenSaturation < OXYGEN_SATURATION_THRESHOLD) {
                String condition = "Hypotensive Hypoxemia detected! ";
                long timestamp = record.getTimestamp();
                Alert alert= new Alert(patientId,condition,timestamp);
                alert.triggerAlert();
            }
        }
    }

    public List<AlertComponent>  getTriggeredAlerts() {
        return AlertExecutor.getTriggeredAlerts();
    }

    public static void clearAlerts() {
        AlertExecutor.getTriggeredAlerts().clear();
    }


}