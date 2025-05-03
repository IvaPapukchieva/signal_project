package com.alerts.alert_decorators;

import com.alerts.Alert;

public interface AlertComponent {


    String getPatientId();

    String getCondition();

    long getTimestamp();

    void triggerAlert();


    String getAlertDetails();
}
