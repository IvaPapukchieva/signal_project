package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class implements the {@link PatientDataGenerator} interface and is responsible for
 * generating simulated blood saturation data for patients. The blood saturation values are
 * calculated based on previous readings, with small random fluctuations. The class ensures
 * that the generated saturation values remain within a healthy range (90% to 100%).
 * Once generated, the data is output using the provided {@link OutputStrategy}.
 */

public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private int[] lastSaturationValues;

    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }
    /**
     * Generates a new blood saturation value for the given patient by simulating small fluctuations
     * based on the last known value. It makes sure the value stays in a healthy range (90-100%) and
     * then uses the given OutputStrategy to output the data, whether it's printing to a file or
     * sending it over TCP.
     *
     * @param patientId       the unique ID of the patient whose blood saturation is being generated
     * @param outputStrategy  how the generated data will be outputted (like printing to a file, or TCP)
     *
     * @throws Exception if anything goes wrong while generating or outputting the data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
