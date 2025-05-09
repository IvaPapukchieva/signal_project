package alert_tests;

import com.alerts.Alert;
import com.alerts.alert_decorators.AlertComponent;
import com.alerts.alert_decorators.PriorityAlertDecorator;
import com.alerts.alert_decorators.RepeatedAlertDecorator;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class AlertDecoratorTest {

        @Test
        void testAlertDecorator() throws InterruptedException {

            AlertComponent testAlert = new Alert("1", "sick", 1714376789001L);
            PriorityAlertDecorator priorityAlert = new PriorityAlertDecorator(testAlert, "Very important");
            RepeatedAlertDecorator repeatedAlert = new RepeatedAlertDecorator(priorityAlert, 10, 3);

          repeatedAlert.triggerAlert();

            String alertDetails = repeatedAlert.toString();

            assertTrue(alertDetails.contains("Patient ID: 1"));
            assertTrue(alertDetails.contains("Condition: sick"));
            assertTrue(alertDetails.contains("Repetition interval: 10 seconds"));
            assertTrue(alertDetails.contains("Priority Level: Very important"));

            Thread.sleep(2000);
        }
    }