package com.alerts.alert_strategies;

import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_decorators.AlertExecutor;
import com.data_management.PatientRecord;

import java.util.List;

public interface AlertStrategy {
    void checkAlert(String patientId, List<PatientRecord> records);
    List<AlertComponent>  getTriggeredAlerts();
}