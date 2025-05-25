package com.alerts.alert_factories;

import com.alerts.Alert;

public class HypotensiveHypoxemiaFactory implements AlertFactory {



    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "Hypotensive Hypoxemia Alert: " + condition, timestamp);
    }
}

