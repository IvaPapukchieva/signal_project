package com.alerts.alert_decorators;

import com.alerts.Alert;

public class PriorityAlertDecorator extends AlertDecorator {
    private final String priorityLevel;

    public PriorityAlertDecorator(AlertComponent decoratedAlert, String priorityLevel){
        super(decoratedAlert);
        this.priorityLevel = priorityLevel;
    }
    public String getPriorityLevel() {

        return this.priorityLevel;
    }
    @Override
    public String getCondition() {
        return " Priority Level: " + getPriorityLevel() +". This patient is: " + super.getCondition() ;


    }


    @Override
    public void triggerAlert() {
        super.triggerAlert();
        System.out.println("Triggering priority alert!" + getCondition());

    }

    @Override
    public String getAlertDetails() {
        return "Priority Level: " + getPriorityLevel() + "\n" + super.getAlertDetails();
    }
}
