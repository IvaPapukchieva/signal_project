package alert_tests;

import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_strategies.BloodOxygenStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OxygenSaturationAlert {
        private final int PATIENT_ID=1;

        @Test
        void testOxygenSaturationAlert() {
            String oxygenSaturation= "Saturation";

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
}
