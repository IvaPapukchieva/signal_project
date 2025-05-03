package alert_tests;

import com.alerts.alert_strategies.BloodPressureStrategy;
import com.alerts.alert_strategies.HeartRateStrategy;
import com.alerts.alert_strategies.HypotensiveHypoxemiaStrategy;
import com.alerts.alert_strategies.BloodOxygenStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class AlertStrategyTest {
    private final int PATIENT_ID=1;

    //test all alerts
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



    }

    @Test
    void testECGAlert() {
        String ecg = "ECG";
        long t1 = 1714376789050L;
        long t2 = 1714376789051L;

        List<PatientRecord> records = new ArrayList<>(List.of(
                new PatientRecord(PATIENT_ID, 110.0, ecg, t1),
                new PatientRecord(PATIENT_ID, 90.0, ecg, t2)
        ));

        HeartRateStrategy alert = new HeartRateStrategy();
        alert.checkAlert("1", records);

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
    }



}
