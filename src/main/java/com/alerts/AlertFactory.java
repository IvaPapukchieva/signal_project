package com.alerts;

import com.alerts.alert_strategies.AlertStrategy;
import com.alerts.alert_strategies.BloodPressureStrategy;
import com.alerts.alert_strategies.HeartRateStrategy;
import com.alerts.alert_strategies.OxygenSaturationStrategy;
import com.data_management.DataStorage;

abstract class AlertFactory {
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
    private DataStorage dataStorage= DataStorage.getInstance();


    public AlertStrategy getAlert(String alert, String patientId, String condition, long timestamp) {
        switch (alert) {
        case "Blood Pressure":
            return new BloodPressureStrategy();
        case "Heart Rate":
            return new HeartRateStrategy();
        case "Oxygen Saturation":
            return new OxygenSaturationStrategy();
        default:
            System.out.println("Unknown alert type.");
            return null;
    }
    }

}
class BloodPressureAlertFactory extends AlertFactory {

    @Override
    public  Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp);

    }
}
class BloodOxygenAlertFactory extends AlertFactory {

    @Override
    public  Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp);
    }
}
class ECGAlertFactory extends AlertFactory {

    @Override
    public  Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp);
    }
}
