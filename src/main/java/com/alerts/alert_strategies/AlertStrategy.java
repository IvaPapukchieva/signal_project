package com.alerts.alert_strategies;

import com.data_management.PatientRecord;

import java.util.List;

public interface AlertStrategy {
    void checkAlert(String patientId, List<PatientRecord> records);
}