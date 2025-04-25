package com.alerts.alert_strategies;

import com.alerts.Alert;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureStrategy implements AlertStrategy{

    /**
     * BloodPressureStrategy implements the AlertStrategy interface to monitor
     * systolic and diastolic blood pressure readings.
     * <p>
     * It triggers alerts if:
     * - Systolic > 180 or < 90
     * - Diastolic > 120 or < 60
     * - A consistent increasing or decreasing trend is detected in the last 3 readings
     */

    private static final double SYSTOLIC_HIGH_THRESHOLD = 180;
    private static final double SYSTOLIC_LOW_THRESHOLD = 90;
    private static final double DIASTOLIC_HIGH_THRESHOLD = 120;
    private static final double DIASTOLIC_LOW_THRESHOLD = 60;
    private static final double TREND_THRESHOLD = 10;
    private static final int TREND_SIZE = 3;

    @Override
    public void checkAlert(String patientId, List<PatientRecord> records) {
        List<Double> systolicValues = new ArrayList<>();
        List<Double> diastolicValues = new ArrayList<>();
        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double value = record.getMeasurementValue();
            String condition = "";
            long timestamp = record.getTimestamp();

            if (type.equals("Systolic")) {
                systolicValues.add(value);
                if (value > SYSTOLIC_HIGH_THRESHOLD || value < SYSTOLIC_LOW_THRESHOLD) {
                    condition = "The systolic blood pressure is Critical! :(";
                    Alert alert= new Alert(patientId,condition,timestamp);
                    alert.triggerAlert();
                } else if (systolicValues.size() == TREND_SIZE) {
                    checkTrend(systolicValues, patientId, timestamp);
                }
                if (systolicValues.size() > TREND_SIZE) {
                    systolicValues.remove(0);
                }
            }

            if (type.equals("Diastolic")) {
                diastolicValues.add(value);
                if (value > DIASTOLIC_HIGH_THRESHOLD || value < DIASTOLIC_LOW_THRESHOLD) {
                    condition = "The diastolic blood pressure is Critical! :(";
                    Alert alert= new Alert(patientId,condition,timestamp);
                    alert.triggerAlert();
                } else if (diastolicValues.size() == TREND_SIZE) {
                    checkTrend(diastolicValues, patientId, timestamp);
                }
                if (diastolicValues.size() > TREND_SIZE) {
                    diastolicValues.remove(0);
                }
            }
        }
    }

    private void checkTrend(List<Double> values, String patientId, long timestamp) {
        double firstValue = values.get(0);
        double secondValue = values.get(1);
        double thirdValue = values.get(2);
        String condition = "";
        if ((secondValue - firstValue > TREND_THRESHOLD) && (thirdValue - secondValue > TREND_THRESHOLD)) {
            condition = "Blood pressure is consistently increasing :( ";
           Alert alert= new Alert(patientId,condition,timestamp);
           alert.triggerAlert();
            values.remove(0);
        } else if ((firstValue - secondValue > TREND_THRESHOLD) && (secondValue - thirdValue > TREND_THRESHOLD)) {
            condition = "Blood pressure is consistently decreasing! :(";
            Alert alert= new Alert(patientId,condition,timestamp);
            alert.triggerAlert();
        }
    }


}
