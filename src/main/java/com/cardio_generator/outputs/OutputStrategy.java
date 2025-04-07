package com.cardio_generator.outputs;

public interface OutputStrategy {
    void output(int patientId, long timestamp, String label, String data);
}
/**
 * This interface represents the skeleton for different output strategies used
 * to handle patient data.
 *
 * @param patientId the ID of the patient
 * @param timestamp the time at which the data is recorded
 * @param label the label identifying the type of data (e.g., "blood level")
 * @param data the actual data to be output
 */

