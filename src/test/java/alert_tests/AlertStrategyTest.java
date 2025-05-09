package alert_tests;

import com.alerts.Alert;
import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_strategies.BloodPressureStrategy;
import com.alerts.alert_strategies.HeartRateStrategy;
import com.alerts.alert_strategies.HypotensiveHypoxemiaStrategy;
import com.alerts.alert_strategies.BloodOxygenStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


class AlertStrategyTest {
    private final int PATIENT_ID=1;

    @Test
    void testOxygenSaturationAlert() {
        String oxygenSaturation= "Oxygen Saturation";

        List<PatientRecord> records = new ArrayList<>(List.of(
                new PatientRecord(PATIENT_ID, 88.0, oxygenSaturation, 1714376789050L),
                new PatientRecord(PATIENT_ID, 75.0, oxygenSaturation, 1714376789051L),
                new PatientRecord(PATIENT_ID, 95.0, oxygenSaturation, 1714376789052L)
        ));


        BloodOxygenStrategy alert = new BloodOxygenStrategy();
        alert.checkAlert("1", records);
        List<AlertComponent> alerts=alert.getTriggeredAlerts();
        System.out.println("THIS IS THE ALERT LIST");
        System.out.println(alerts);

        assertEquals(3, alerts.size());
        String firstAlertDetails = alerts.get(0).toString();
        assertTrue(firstAlertDetails.contains("Priority Level: High"));
        assertTrue(firstAlertDetails.contains("Blood Oxygen Alert"));

        String lastAlertDetails = alerts.get(2).toString();
        assertTrue(lastAlertDetails.contains("under the drop limit"));
        assertTrue(lastAlertDetails.contains("Priority Level: Medium"));


    }

    @Test
    void testECGAlert() {
        String ecg = "ECG";
        long t1 = 1714376789050L;
        long t2 = 1714376789051L;

        List<PatientRecord> records = new ArrayList<>(List.of(
                new PatientRecord(PATIENT_ID, 120.0, ecg, t1),
                new PatientRecord(PATIENT_ID, 190.0, ecg, t2)
        ));

        HeartRateStrategy alert = new HeartRateStrategy();
        alert.checkAlert("1", records);
        List<AlertComponent> alerts=alert.getTriggeredAlerts();
        System.out.println("THIS IS THE ALERT LIST");
        System.out.println(alerts);
        assertEquals(2, alerts.size());
        String firstAlert = alerts.get(0).toString();
        assertTrue(firstAlert.contains("Heart Rate Alert:"));
        assertTrue(firstAlert.contains("Priority Level"));

        String secondAlert = alerts.get(1).toString();
        assertTrue(secondAlert.contains("Priority Level: High"));
        assertTrue(secondAlert.contains("Heart Rate is too high! "));

    }

    @Test
    void testBloodPressureAlert() {
        int patientID = 1;
        String systolic = "Systolic";
        String diastolic = "Diastolic";
        long t1 = 1714376789001L;
        long t2 = 1714376789002L;
        long t3 = 1714376789003L;

        List<PatientRecord> records = new ArrayList<>(List.of(
                new PatientRecord(patientID, 170.0, systolic, t1),
                new PatientRecord(patientID, 105.0, systolic, t2),
                new PatientRecord(patientID, 94.0, systolic, t3),
                new PatientRecord(patientID, 1000.0, systolic, t1),
                new PatientRecord(patientID, 97.0, diastolic, t1),
                new PatientRecord(patientID, 109.0, diastolic, t2),
                new PatientRecord(patientID, 120.0, diastolic, t3),
                new PatientRecord(patientID, 1000.0, diastolic, t1)
        ));

        BloodPressureStrategy alert = new BloodPressureStrategy();
        alert.checkAlert("1", records);
        List<AlertComponent> alerts=alert.getTriggeredAlerts();
        System.out.println("THIS IS THE ALERT LIST");
        System.out.println(alerts);

        assertEquals(4, alerts.size());

        assertTrue(alerts.get(0).toString().contains("Blood pressure is consistently decreasing!"));
        assertTrue(alerts.get(0).toString().contains("Priority Level: Medium"));

        assertTrue(alerts.get(1).toString().contains("systolic blood pressure is Critical!"));
        assertTrue(alerts.get(1).toString().contains("Priority Level: High"));

        assertTrue(alerts.get(2).toString().contains("Blood pressure is consistently increasing!"));
        assertTrue(alerts.get(2).toString().contains("Priority Level: Medium"));

        assertTrue(alerts.get(3).toString().contains("diastolic blood pressure is Critical!"));
        assertTrue(alerts.get(3).toString().contains("Priority Level: High"));
    }

    @Test
    void HypotensiveHypoxemiaAlertTest() {
        String oxygenSaturation= "Oxygen Saturation";
        String systolic = "Systolic";
        long t1 = 1714376789001L;
        long t2 = 1714376789002L;
        long t3 = 1714376789003L;

        List<PatientRecord> records = new ArrayList<>(List.of(
                new PatientRecord(PATIENT_ID, 85.0, systolic, t1),
                new PatientRecord(PATIENT_ID, 91.0, oxygenSaturation, t2),
                new PatientRecord(PATIENT_ID, 89.0, systolic, t2),
                new PatientRecord(PATIENT_ID, 90.0, oxygenSaturation, t3)
        ));

        HypotensiveHypoxemiaStrategy alert = new  HypotensiveHypoxemiaStrategy ();
        alert.checkAlert("1", records);


    }



}
