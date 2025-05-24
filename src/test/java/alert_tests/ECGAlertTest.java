package alert_tests;

import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_strategies.HeartRateStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ECGAlertTest {


    @Test
    void testECGAlert() {
        String ecg = "ECG";
        long t1 = 1714376789050L;
        long t2 = 1714376789051L;

        List<PatientRecord> records = new ArrayList<>(List.of(
                new PatientRecord(1, 120.0, ecg, t1),
                new PatientRecord(1, 190.0, ecg, t2)
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
}
