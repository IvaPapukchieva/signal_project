package com.alerts.alert_strategies;

import com.alerts.Alert;
import com.alerts.alert_factories.AlertFactory;
import com.alerts.alert_factories.BloodOxygenAlertFactory;
import com.data_management.PatientRecord;

import java.util.List;

public class BloodOxygenStrategy implements AlertStrategy {

    private final AlertFactory bloodOxygenAlertFactory;

    private static final double THRESHOLD = 92;
    private static final double DROP_THRESHOLD = 5;
    private static final long DROP_LIMIT = 600000;

    public BloodOxygenStrategy() {
        this.bloodOxygenAlertFactory = new BloodOxygenAlertFactory();
    }
    @Override
    public void checkAlert(String patientId, List<PatientRecord> records) {
        double prevSaturation = -1;
        long prevTimestamp = -1;

        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double value = record.getMeasurementValue();
            long timestamp = record.getTimestamp();
            String condition;

            if (type.equals("Oxygen Saturation")) {

                if (value < THRESHOLD) {
                    condition = "Oxygen saturation is too low!";
                    Alert alert= new Alert(patientId,condition,timestamp);
                    alert.triggerAlert();
                }

                if (prevSaturation != -1 && timestamp - prevTimestamp <= DROP_LIMIT && prevSaturation - value >= DROP_THRESHOLD) {
                    condition = "Oxygen saturation is under the drop limit!";
                    Alert alert= new Alert(patientId,condition,timestamp);
                    alert.triggerAlert();
                }
                // Update previous values
                prevSaturation = value;
                prevTimestamp = timestamp;
            }
        }
    }


}