package com.alerts.alert_factories;

import com.alerts.Alert;

public abstract interface AlertFactory {
    public abstract Alert createAlert(String patientId, String condition, long timestamp);


}




