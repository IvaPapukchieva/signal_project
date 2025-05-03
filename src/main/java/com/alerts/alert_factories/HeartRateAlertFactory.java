package com.alerts.alert_factories;

import com.alerts.Alert;

public class HeartRateAlertFactory implements AlertFactory {

   @Override
   public  Alert createAlert(String patientId, String condition, long timestamp) {
       return new Alert(patientId, "Heart Rate Alert: "+ condition, timestamp);
   }
}