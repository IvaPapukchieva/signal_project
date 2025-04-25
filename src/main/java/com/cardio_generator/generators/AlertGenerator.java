package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

import java.util.Random;

public class AlertGenerator implements PatientDataGenerator {
    /**
     * This class implements {@link PatientDataGenerator } and is used to simulate alert data for patients
     * (e.g.,  emergencies).
     * <p>
     * It keeps track of which patients are currently in an alert state.
     */


    public static final Random randomGenerator = new Random();
//    this should be in lowerCamelCase
    private boolean[] alertStates; // false = resolved, true = pressed

    public AlertGenerator(int patientCount) {

        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates an alert status for a given patient.
     * <p>
     * - If the patient already has an active alert, there's a 90% chance it gets resolved.
     * - If the patient is not in an alert state, there's a small chance an alert gets triggered.
     *
     * @param patientId the ID of the patient
     * @param outputStrategy the strategy used to output the generated data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //    this should be in lowerCamelCase
//                rename to alert rate, because it is not very clear
                double alertRate = 0.1; // Average rate (alerts per period), adjust based on desired frequency
//                p is not very clear - change the name to probabilityOfAlert
                double probabilityOfAlert = -Math.expm1(-alertRate); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < probabilityOfAlert;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
