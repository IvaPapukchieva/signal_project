package com.alerts.alert_decorators;

import java.util.ArrayList;
import java.util.List;

public class AlertExecutor {

    /**
     * Responsible for executing decorated alerts.
     * <p>
     * This class creates alert decorators to apply both priority tagging
     * and repeated alert functionality, then triggers the final alert.
     * </p>
     */
    private static List<AlertComponent> alertComponentList = new ArrayList<>();

   public static void executeAlert(AlertComponent decoratedAlert,
                                     String priorityLevel,
                                     long interval,
                                     double amountOfRepetitions){

       AlertComponent priorityAlert= new PriorityAlertDecorator(decoratedAlert,priorityLevel);
       AlertComponent repeatedAlert = new RepeatedAlertDecorator(priorityAlert,interval,amountOfRepetitions);
       repeatedAlert.triggerAlert();
       addTriggeredAlerts(repeatedAlert);

   }

    public static void addTriggeredAlerts(AlertComponent alert) {
        alertComponentList.add(alert);

    }

    public static List<AlertComponent> getTriggeredAlerts() {
        return alertComponentList;
    }





}
