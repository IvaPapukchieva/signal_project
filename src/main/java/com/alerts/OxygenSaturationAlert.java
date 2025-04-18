package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class OxygenSaturationAlert implements AlertStrategy {

    /**
     * The OxygenSaturationAlert class implements AlertStrategy and is responsible for
     * evaluating oxygen saturation records to detect low oxygen saturation and rapid
     * drops in saturation levels, triggering alerts accordingly.
     */

    @Override
    public void evaluateAlert(String patientId, List<PatientRecord> records) {
        double prevSaturation = -1;
        long prevTimestamp = -1;

        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double value = record.getMeasurementValue();
            long timestamp = record.getTimestamp();
            String condition;

            if (type.equals("Oxygen Saturation")) {
                if (value < 92) {
                    condition = "Oxygen saturation is too low! :(";
                    triggerAlert(new Alert(patientId, condition, timestamp));
                }

                // Check for rapid drop
                if (prevSaturation != -1 && timestamp - prevTimestamp <= 600000 && prevSaturation - value >= 5) {
                    condition = "Oxygen saturation dropped rapidly! :(";
                    triggerAlert(new Alert(patientId, condition, timestamp));
                }

                prevSaturation = value;
                prevTimestamp = timestamp;
            }
        }
    }

    private void triggerAlert(Alert alert) {
        System.out.println("Alert");
        System.out.println(alert.getCondition());
    }
}