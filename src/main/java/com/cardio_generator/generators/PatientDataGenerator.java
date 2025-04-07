package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;


public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
/**
 * This interface represents the skeleton for all patient data generators.
 * It defines a method to generate data for a given patient using a specific output strategy.
 *
 * @param patientId the ID of the patient
 * @param outputStrategy the strategy used to output the generated data
 */
