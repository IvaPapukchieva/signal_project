package com.alerts.alert_decorators;

import com.alerts.Alert;

import java.util.List;

public interface AlertComponent {


    String getPatientId();

    String getCondition();

    long getTimestamp();

    void triggerAlert();

    String toString();



}
