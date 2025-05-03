package alert_tests;

import com.alerts.Alert;
import com.alerts.alert_factories.AlertFactory;
import com.alerts.alert_factories.BloodOxygenAlertFactory;
import com.alerts.alert_factories.BloodPressureAlertFactory;
import com.alerts.alert_factories.HeartRateAlertFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class AlertFactoryTest {


    @Test
    void BloodOxygenAlertFactory(){

        AlertFactory bloodOxygenAlertFactory=new BloodOxygenAlertFactory();
        Alert alert = bloodOxygenAlertFactory.createAlert("1","Oxygen saturation is too low!",1714376789001L);
        Assertions.assertEquals("Blood Oxygen Alert: Oxygen saturation is too low!", alert.getCondition());

    }
    @Test
    void BloodPressureAlertFactory(){
        AlertFactory bloodPressureAlertFactory=new BloodPressureAlertFactory();
        Alert alert = bloodPressureAlertFactory.createAlert("1","The systolic blood pressure is Critical!",1714376789001L);
        Assertions.assertEquals("Blood Pressure Alert: The systolic blood pressure is Critical!", alert.getCondition());


    }
    @Test
    void HeartRateAlertFactory(){
        AlertFactory heartRateAlertFactory=new HeartRateAlertFactory();
        Alert alert = heartRateAlertFactory.createAlert("1","Heart Rate is too high!",1714376789001L);
        Assertions.assertEquals("Heart Rate Alert: Heart Rate is too high!", alert.getCondition());


    }
}
