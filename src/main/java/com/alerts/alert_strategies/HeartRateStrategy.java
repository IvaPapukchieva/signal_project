package com.alerts.alert_strategies;

import com.alerts.Alert;
import com.data_management.PatientRecord;

import java.util.List;

public class HeartRateStrategy implements AlertStrategy {
    /**
     * The ECGAlert class implements the AlertStrategy interface and is responsible for
     * evaluating ECG records to detect abnormal ECG data. If the value exceeds a threshold,
     * an alert is triggered indicating an abnormal ECG reading.
     */
    private static final int THRESHOLD = 100;

    @Override
    public void checkAlert(String patientId, List<PatientRecord> records) {
        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double value = record.getMeasurementValue();
            String condition;
            long timestamp = record.getTimestamp();

            if (type.equals("ECG") && value > THRESHOLD) {
                condition = "ECG data abnormal! :(";
                Alert alert= new Alert(patientId,condition,timestamp);
                alert.triggerAlert();
            }
        }
    }


}