package com.alerts;

import com.alerts.alert_decorators.AlertComponent;

// Represents an alert
public class Alert implements AlertComponent {
    private String patientId;
    private String condition;
    private long timestamp;

    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public long getTimestamp() {

        return timestamp;
    }


    public void triggerAlert() {
        System.out.println("An alert was triggered!\n" +
    "Reason: " + getCondition());
    }

    @Override
    public String getAlertDetails() {
        return "This alert has the following details:\n" +
                "Patient ID: " + getPatientId() + "\n" +
                "Condition: " + getCondition() + "\n" +
                "Timestamp: " + getTimestamp();
    }
}
