package com.alerts.alert_strategies;

import com.alerts.Alert;
import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_decorators.AlertExecutor;
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
         clearAlerts();
        double prevSaturation = -1;
        long prevTimestamp = -1;

        for (PatientRecord record : records) {
            String type = record.getRecordType();
            double value = record.getMeasurementValue();
            long timestamp = record.getTimestamp();
            String condition;
            if (type.equals("Saturation")) {
                if (value < THRESHOLD) {
                    condition = "Oxygen saturation is too low!";
                    Alert alert= bloodOxygenAlertFactory.createAlert(patientId,condition,timestamp);
                    AlertExecutor.executeAlert(alert,"High",10,2);
                }
                if (prevSaturation != -1 && timestamp - prevTimestamp <= DROP_LIMIT && prevSaturation - value >= DROP_THRESHOLD) {
                    condition = "Oxygen saturation is under the drop limit!";
                    Alert alert= new Alert(patientId,condition,timestamp);
                    AlertExecutor.executeAlert(alert,"Medium",5,1);
                }
                prevSaturation = value;
                prevTimestamp = timestamp;
            }
        }
    }


    public List<AlertComponent>  getTriggeredAlerts() {
        return AlertExecutor.getTriggeredAlerts();
    }

    public static void clearAlerts() {
        AlertExecutor.getTriggeredAlerts().clear();
    }

}