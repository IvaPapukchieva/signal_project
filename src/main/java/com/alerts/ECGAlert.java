package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class ECGAlert implements AlertStrategy {

    @Override
    public void evaluateAlert(String patientId, List<PatientRecord> records) {
        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double value = record.getMeasurementValue();
            String condition;
            long timestamp = record.getTimestamp();

            if (type.equals("ECG") && value > 100) {
                condition = "ECG data abnormal! :(";
                triggerAlert(new Alert(patientId, condition, timestamp));
            }
        }
    }

    private void triggerAlert(Alert alert) {
        System.out.println("Alert");
        System.out.println(alert.getCondition());
    }
}