package com.alerts.alert_decorators;

import com.alerts.Alert;

public class PriorityAlertDecorator extends AlertDecorator {
    private final String priorityLevel;

    public PriorityAlertDecorator(Alert decoratedAlert, String priorityLevel){
        super(decoratedAlert);
        this.priorityLevel = priorityLevel;
    }
    public String getPriorityLevel() {

        return this.priorityLevel;
    }
    @Override
    public String getCondition() {
        return super.getCondition() + " Priority Level: " + getPriorityLevel();


    }


    public void printCondition() {
        System.out.println(getCondition());

    }


    @Override
    public void triggerAlert() {
        super.triggerAlert();

    }
}
