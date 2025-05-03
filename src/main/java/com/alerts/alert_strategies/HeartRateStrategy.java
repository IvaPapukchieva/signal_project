package com.alerts.alert_strategies;

import com.alerts.Alert;
import com.alerts.alert_factories.AlertFactory;
import com.alerts.alert_factories.HeartRateAlertFactory;
import com.data_management.PatientRecord;

import java.util.List;

public class HeartRateStrategy implements AlertStrategy {

    private final AlertFactory heartRateAlertFactory;

    private static final int THRESHOLD = 100;

    public HeartRateStrategy() {
        this.heartRateAlertFactory = new HeartRateAlertFactory();
    }

    @Override
    public void checkAlert(String patientId, List<PatientRecord> records) {
        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double value = record.getMeasurementValue();
            String condition;
            long timestamp = record.getTimestamp();

            if (type.equals("ECG") && value > THRESHOLD) {
                condition = "Heart Rate is too high! ";
                Alert alert= new Alert(patientId,condition,timestamp);
                alert.triggerAlert();
            }
        }
    }


}