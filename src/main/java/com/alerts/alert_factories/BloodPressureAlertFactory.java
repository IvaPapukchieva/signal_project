package com.alerts.alert_factories;

import com.alerts.Alert;

public class BloodPressureAlertFactory implements AlertFactory {

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "Blood Pressure Alert: "+ condition, timestamp);

    }
}