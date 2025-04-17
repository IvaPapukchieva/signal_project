package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public interface AlertStrategy {
    void evaluateAlert(String patientId, List<PatientRecord> records);
}