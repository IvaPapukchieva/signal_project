package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

import java.util.Random;

public class ECGDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private final double[] lastEcgValues;
    private final double[] patientTime; // Simulated time per patient
    private static final double PI = Math.PI;

    public ECGDataGenerator(int patientCount) {
        lastEcgValues = new double[patientCount + 1];
        patientTime = new double[patientCount + 1];
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {

            patientTime[patientId] += 0.01;

            double ecgValue = simulateEcgWaveform(patientId, patientTime[patientId]);
            outputStrategy.output(patientId, System.currentTimeMillis(), "ECG", Double.toString(ecgValue));
            lastEcgValues[patientId] = ecgValue;

        } catch (Exception e) {
            System.err.println("An error occurred while generating ECG data for patient " + patientId);
            e.printStackTrace();
        }
    }

    private double simulateEcgWaveform(int patientId, double t) {
        double heartRate = 60.0 + random.nextDouble() * 20.0;
        double ecgFrequency = heartRate / 60.0; // Hz
        double cyclePosition = t % (1.0 / ecgFrequency);

        double pWave = 0.1 * Math.sin(2 * PI * ecgFrequency * t);
        double qrsComplex = 1.2 * qrsPulse(cyclePosition, 0.25, 0.025);
        double tWave = 0.2 * Math.sin(2 * PI * ecgFrequency * t + PI / 3);

        double noise = random.nextGaussian() * 0.02;
        return (pWave + qrsComplex + tWave + noise) * 1000;
    }

    private double qrsPulse(double t, double center, double width) {
        return Math.exp(-Math.pow((t - center) / width, 2));
    }
}