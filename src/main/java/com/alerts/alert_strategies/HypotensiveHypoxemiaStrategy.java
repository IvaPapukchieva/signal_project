package com.alerts.alert_strategies;

import com.alerts.Alert;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * The HypotensiveHypoxemiaAlert class implements AlertStrategy and is responsible for
 * evaluating records to detect hypotensive hypoxemia. It checks if both systolic
 * blood pressure is below 90 and oxygen saturation is below 92, triggering an alert if true.
 */
public class HypotensiveHypoxemiaStrategy implements AlertStrategy {

    private static final double SYSTOLIC_THRESHOLD = 90;
    private static final double OXYGEN_SATURATION_THRESHOLD = 92;

    @Override
    public void checkAlert(String patientId, List<PatientRecord> records) {
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
                String condition = "Hypotensive Hypoxemia detected! :(";
                long timestamp = record.getTimestamp();
                Alert alert= new Alert(patientId,condition,timestamp);
                alert.triggerAlert();
            }
        }
    }


}