package com.alerts.alert_strategies;

import com.alerts.Alert;
import com.data_management.PatientRecord;

import java.util.List;

public class OxygenSaturationStrategy implements AlertStrategy {

    /**
     * This class implements AlertStrategy and is responsible for
     * evaluating oxygen saturation records.
     */

    private static final double OXYGEN_SATURATION_THRESHOLD = 92;
    private static final double OXYGEN_SATURATION_DROP_THRESHOLD = 5;
    private static final long RAPID_DROP_TIME_LIMIT = 600000;

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


                if (value < OXYGEN_SATURATION_THRESHOLD) {
                    condition = "Oxygen saturation is too low! :(";
                    Alert alert= new Alert(patientId,condition,timestamp);
                    alert.triggerAlert();
                }


                if (prevSaturation != -1 && timestamp - prevTimestamp <= RAPID_DROP_TIME_LIMIT && prevSaturation - value >= OXYGEN_SATURATION_DROP_THRESHOLD) {
                    condition = "Oxygen saturation dropped rapidly! :(";
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