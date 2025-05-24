package alert_tests;

import com.alerts.alert_strategies.HypotensiveHypoxemiaStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

    class HypotensiveHypoxemiaAlertTest {

        @Test
        void testHypotensiveHypoxemiaAlert() {
            String oxygenSaturation = "Oxygen Saturation";
            String systolic = "Systolic";
            long t1 = 1714376789001L;
            long t2 = 1714376789002L;
            long t3 = 1714376789003L;

            List<PatientRecord> records = new ArrayList<>(List.of(
                    new PatientRecord(1, 85.0, systolic, t1),
                    new PatientRecord(1, 91.0, oxygenSaturation, t2),
                    new PatientRecord(1, 89.0, systolic, t2),
                    new PatientRecord(1, 90.0, oxygenSaturation, t3)
            ));

            HypotensiveHypoxemiaStrategy alert = new HypotensiveHypoxemiaStrategy();
            alert.checkAlert("1", records);


        }
    }



