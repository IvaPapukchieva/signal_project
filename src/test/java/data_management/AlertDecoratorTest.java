package data_management;

import com.alerts.Alert;
import com.alerts.alert_decorators.AlertDecorator;
import com.alerts.alert_decorators.PriorityAlertDecorator;
import com.alerts.alert_decorators.RepeatedAlertDecorator;
import org.junit.jupiter.api.Test;

public class AlertDecoratorTest {
    //tests for decorators
    @Test
    void testAlertDecorator(){

        Alert testAlert=new Alert(String.valueOf(1),"Sick",1714376789001L);
        AlertDecorator fancyDecoratedAlert=new AlertDecorator(testAlert);
        RepeatedAlertDecorator repeatedAlertDecorator=new RepeatedAlertDecorator(fancyDecoratedAlert,10);
        PriorityAlertDecorator priorityAlertDecorator=new PriorityAlertDecorator(fancyDecoratedAlert,"Very important!");
        repeatedAlertDecorator.checkAlertForTime();
        priorityAlertDecorator.printCondition();


    }
}
