package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class HypotensiveHypoxemiaAlert implements AlertStrategy {

    @Override
    public void evaluateAlert(String patientId, List<PatientRecord> records) {
        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double systolic = -1;
            double oxygenSaturation = -1;

            if (type.equals("Systolic")) {
                systolic = record.getMeasurementValue();
            }

            if (type.equals("Oxygen Saturation")) {
                oxygenSaturation = record.getMeasurementValue();
            }

            if (systolic < 90 && oxygenSaturation < 92) {
                String condition = "Hypotensive Hypoxemia detected! :(";
                long timestamp = record.getTimestamp();
                triggerAlert(new Alert(patientId, condition, timestamp));
            }
        }
    }

    private void triggerAlert(Alert alert) {
        System.out.println("Alert");
        System.out.println(alert.getCondition());
    }
}