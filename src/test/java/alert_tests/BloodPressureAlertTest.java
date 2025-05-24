package alert_tests;

import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_strategies.BloodPressureStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BloodPressureAlertTest {

    @Test
    void testBloodPressureAlert() {
        int patientID = 1;
        String systolic = "SystolicPressure";
        String diastolic = "DiastolicPressure";
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

    }
