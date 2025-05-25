package com.alerts.alert_strategies;

import com.alerts.Alert;
import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_decorators.AlertExecutor;
import com.alerts.alert_factories.AlertFactory;
import com.alerts.alert_factories.HypotensiveHypoxemiaFactory;
import com.data_management.PatientRecord;

import java.util.List;

public class HypotensiveHypoxemiaStrategy implements AlertStrategy {

    private final AlertFactory hypotensiveHypoxemiaFactory;

    private static final double SYSTOLIC_THRESHOLD = 90;
    private static final double OXYGEN_SATURATION_THRESHOLD = 92;

    public HypotensiveHypoxemiaStrategy() {
        this.hypotensiveHypoxemiaFactory = new HypotensiveHypoxemiaFactory();
    }

    @Override
    public void checkAlert(String patientId, List<PatientRecord> records) {
        clearAlerts();
        double systolic = -1;
        double oxygenSaturation = -1;

        for (PatientRecord record : records) {
            String type = record.getRecordType();
            if (type.equals("SystolicPressure")) {
                systolic = record.getMeasurementValue();
            }

            if (type.equals("Saturation")) {
                oxygenSaturation = record.getMeasurementValue();
            }
            if(systolic!=-1 && oxygenSaturation!=-1){

            if (systolic < SYSTOLIC_THRESHOLD && oxygenSaturation < OXYGEN_SATURATION_THRESHOLD) {
                String condition = "Hypotensive Hypoxemia detected! ";
                long timestamp = record.getTimestamp();
                Alert alert= hypotensiveHypoxemiaFactory.createAlert(patientId,condition,timestamp);
                AlertExecutor.executeAlert(alert,"High",10,2);
            }
        }}
    }

    public List<AlertComponent>  getTriggeredAlerts() {
        return AlertExecutor.getTriggeredAlerts();
    }

    public static void clearAlerts() {
        AlertExecutor.getTriggeredAlerts().clear();
    }


}