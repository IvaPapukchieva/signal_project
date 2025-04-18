package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * The BloodPressureAlert class implements AlertStrategy and is responsible for
 * evaluating blood pressure records to detect critical conditions.
 * It checks for critical systolic and diastolic blood pressure values,
 * as well as trends in these values over time.
 */
public class BloodPressureAlert implements AlertStrategy {

    @Override
    public void evaluateAlert(String patientId, List<PatientRecord> records) {
        List<Double> systolicValues = new ArrayList<>();
        List<Double> diastolicValues = new ArrayList<>();

        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double value = record.getMeasurementValue();
            String condition = "";
            long timestamp = record.getTimestamp();

            if (type.equals("Systolic")) {
                if (value > 180 || value < 90) {
                    condition = "The systolic blood pressure is Critical! :(";
                    triggerAlert(new Alert(patientId, condition, timestamp));
                } else if (systolicValues.size() == 3) {
                    checkTrend(systolicValues, condition, patientId, timestamp);
                } else {
                    systolicValues.add(value);
                }
            }

            if (type.equals("Diastolic")) {
                if (value > 120 || value < 60) {
                    condition = "The diastolic blood pressure is Critical! :(";
                    triggerAlert(new Alert(patientId, condition, timestamp));
                } else if (diastolicValues.size() == 3) {
                    checkTrend(diastolicValues, condition, patientId, timestamp);
                } else {
                    diastolicValues.add(value);
                }
            }
        }
    }

    private void checkTrend(List<Double> values, String condition, String patientId, long timestamp) {
        double firstValue = values.get(0);
        double secondValue = values.get(1);
        double thirdValue = values.get(2);

        if ((secondValue - firstValue > 10) && (thirdValue - secondValue > 10)) {
            condition = "Blood pressure is consistently increasing :( ";
            triggerAlert(new Alert(patientId, condition, timestamp));
            values.remove(0);
        } else if ((firstValue - secondValue > 10) && (secondValue - thirdValue > 10)) {
            condition = "Blood pressure is consistently decreasing! :(";
            triggerAlert(new Alert(patientId, condition, timestamp));
        }
    }

    private void triggerAlert(Alert alert) {
        System.out.println("Alert");
        System.out.println(alert.getCondition());
    }
}