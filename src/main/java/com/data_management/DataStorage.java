package com.data_management;

import com.alerts.AlertGenerator;
import com.cardio_generator.generators.*;
import com.cardio_generator.outputs.WebSocketOutputStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Manages storage and retrieval of patient data within a healthcare monitoring
 * system.
 * This class serves as a repository for all patient records, organized by
 * patient IDs.
 */
public final class DataStorage {
    private final Map<Integer, Patient> patientMap; // Stores patient objects indexed by their unique patient ID.
    private static DataStorage dataStorage;


    public static DataStorage getInstance() {
        if (dataStorage == null) {
            dataStorage = new DataStorage();
        }
        return dataStorage;
    }

    /**
     * Constructs a new instance of DataStorage, initializing the underlying storage
     * structure.
     */
    public DataStorage() {
        this.patientMap = new HashMap<>();
    }

    /**
     * Adds or updates patient data in the storage.
     * If the patient does not exist, a new Patient object is created and added to
     * the storage.
     * Otherwise, the new data is added to the existing patient's records.
     *
     * @param patientId        the unique identifier of the patient
     * @param measurementValue the value of the health metric being recorded
     * @param recordType       the type of record, e.g., "HeartRate",
     *                         "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in
     *                         milliseconds since the Unix epoch
     */
    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(measurementValue, recordType, timestamp);
    }

    /**
     * Retrieves a list of PatientRecord objects for a specific patient, filtered by
     * a time range.
     *
     * @param patientId the unique identifier of the patient whose records are to be
     *                  retrieved
     * @param startTime the start of the time range, in milliseconds since the Unix
     *                  epoch
     * @param endTime   the end of the time range, in milliseconds since the Unix
     *                  epoch
     * @return a list of PatientRecord objects that fall within the specified time
     * range
     */
    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        System.out.println(patient);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>(); // return an empty list if no patient is found
    }

    public List<PatientRecord> getAllRecords(int patientId) {
        Patient patient = patientMap.get(patientId);
        System.out.println(patient);
        if (patient != null) {
            return patient.getAllRecords();
        }
        return new ArrayList<>(); // return an empty list if no patient is found
    }

    /**
     * Retrieves a collection of all patients stored in the data storage.
     *
     * @return a list of all patients
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    /**
     * The main method for the DataStorage class.
     * Initializes the system, reads data into storage, and continuously monitors
     * and evaluates patient data.
     */

    public void clear() {
        patientMap.clear();
    }


    public static void main(String[] args) throws IOException {
        // DataReader is not defined in this scope, should be initialized appropriately.
        // DataReader reader = new SomeDataReaderImplementation("path/to/data");
//        DataStorage storage = new DataStorage();

        // Assuming the reader has been properly initialized and can read data into the
        // storage
        // reader.readData(storage);

        // Example of using DataStorage to retrieve and print records for a patient
//        List<PatientRecord> records = storage.getRecords(1, 1700000000000L, 1800000000000L);
//        for (PatientRecord record : records) {
//            System.out.println("Record for Patient ID: " + record.getPatientId() +
//                    ", Type: " + record.getRecordType() +
//                    ", Data: " + record.getMeasurementValue() +
//                    ", Timestamp: " + record.getTimestamp());
//        }

        // Initialize the AlertGenerator with the storage
        AlertGenerator alertGenerator = new AlertGenerator();

        int port = 8893;
        WebSocketOutputStrategy strategy = new WebSocketOutputStrategy(port);
        DataStorage dataStorage = DataStorage.getInstance();

        // Connect to the WebSocket as a client to receive data in real-time
        DataReader reader = new WebSocketDataReader("ws://localhost:8893");
        reader.readData(dataStorage);

        int patientCount = 5;

        PatientDataGenerator ecgDataGenerator = new ECGDataGenerator(patientCount);
        PatientDataGenerator bloodSaturationDataGenerator = new BloodSaturationDataGenerator(patientCount);
        PatientDataGenerator bloodPressureDataGenerator = new BloodPressureDataGenerator(patientCount);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            for (int i = 0; i < patientCount; i++) {
                for (int j = 0; j < 5; j++) {
                    ecgDataGenerator.generate(i, strategy);
                    bloodSaturationDataGenerator.generate(i, strategy);
                    bloodPressureDataGenerator.generate(i, strategy);
                }
            }

            // Evaluate alerts for each patient
            for (Patient patient : dataStorage.getAllPatients()) {
                alertGenerator.evaluateData(patient);
            }

        }, 0, 5, TimeUnit.SECONDS);


    }
}