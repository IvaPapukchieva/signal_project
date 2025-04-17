package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.AlertGenerator;
import com.data_management.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() {

        DataStorage storage = new DataStorage();

        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    void testFileDataReader() throws IOException {
        FileWriter writer = new FileWriter("test_fileDataReader.txt");
        writer.write("patientId,timestamp,recordType,value\n");
//        //    int patientId, double measurementValue, String recordType, long timestamp)
        writer.write("1,100.0,WhiteBloodCells,1714376789050\n");
        writer.write("1,200.0,WhiteBloodCells,1714376789051\n");
        writer.close();

        FileDataReader reader = new FileDataReader("test_fileDataReader.txt");
        DataStorage storage = new DataStorage();
        reader.readData(storage);

        List<PatientRecord> records = storage.getRecords(1, 1714376789040L, 1714376789060L); // make sure range includes both
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(200.0, records.get(1).getMeasurementValue());
    }

//    @Test
//    void testEvaluateData() throws IOException {
//        Patient patient0=new Patient(20);
//        patient0.addRecord(2000,"Systolic", 6);
//        DataStorage storage =new DataStorage();
//        storage.addPatientData(20,2000,"Systolic", 6);
//        AlertGenerator generator=new AlertGenerator(storage);
//        generator.evaluateData(patient0);
//
//        assertEquals();
//
//
//    }




}
