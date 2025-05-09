package com.alerts.alert_decorators;

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
        super.getCondition();
        return " Priority Level: " + getPriorityLevel() ;


    }


    @Override
    public void triggerAlert() {
        super.triggerAlert();
        System.out.println("Triggering priority alert!" + getCondition());

    }


    @Override
    public String toString() {
        return "Priority Level: " + getPriorityLevel() + "\n" + super.toString();
    }
}
