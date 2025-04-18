package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.AlertGenerator;
import com.alerts.ECGAlert;
import com.alerts.HypotensiveHypoxemiaAlert;
import com.alerts.OxygenSaturationAlert;
import com.data_management.*;
import org.junit.jupiter.api.Test;
import com.alerts.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class DataStorageTest {

//    test adding and getting records
    @Test
    void testAddAndGetRecords() {

        DataStorage storage = new DataStorage();

        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

//    test the fileDataReader
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

//test all alerts
    @Test
    void testHypotensiveHypoxemiaAlert() {
        PatientRecord record1 = new PatientRecord(1, 85.0, "Systolic", 1714376789050L); // Hypotensive
        PatientRecord record2 = new PatientRecord(1, 91.0, "Oxygen Saturation", 1714376789051L); // Normal Oxygen Saturation
        PatientRecord record3 = new PatientRecord(1, 89.0, "Systolic", 1714376789052L); // Hypotensive again
        PatientRecord record4 = new PatientRecord(1, 90.0, "Oxygen Saturation", 1714376789053L); // Oxygen Saturation normal

        List<PatientRecord> records = new ArrayList<>();
        records.add(record1);
        records.add(record2);
        records.add(record3);
        records.add(record4);

        HypotensiveHypoxemiaAlert alert = new HypotensiveHypoxemiaAlert();
        alert.evaluateAlert("patient123", records);

    }

    @Test
    void testOxygenSaturationAlert() {

        PatientRecord record1 = new PatientRecord(1, 88.0, "Oxygen Saturation", 1714376789050L); // Oxygen saturation too low
        PatientRecord record2 = new PatientRecord(1, 75.0, "Oxygen Saturation", 1714376789051L); // Oxygen saturation dropped rapidly
        PatientRecord record3 = new PatientRecord(1, 95.0, "Oxygen Saturation", 1714376789052L); // Normal saturation
        List<PatientRecord> records = new ArrayList<>();
        records.add(record1);
        records.add(record2);
        records.add(record3);
        OxygenSaturationAlert alert = new OxygenSaturationAlert();
        alert.evaluateAlert("patient123", records);


    }

    @Test
    void testECGAlert() {
        PatientRecord record1 = new PatientRecord(1, 110.0, "ECG", 1714376789050L); // Abnormal ECG
        PatientRecord record2 = new PatientRecord(1, 90.0, "ECG", 1714376789051L);  // Normal ECG
        List<PatientRecord> records = new ArrayList<>();
        records.add(record1);
        records.add(record2);

        ECGAlert alert = new ECGAlert();
        alert.evaluateAlert("patient123", records);

    }

    @Test
    void testBloodPressureAlert() {
        PatientRecord record1 = new PatientRecord(1, 185.0, "Systolic", 1714376789050L); // Critical systolic pressure
        PatientRecord record2 = new PatientRecord(1, 130.0, "Diastolic", 1714376789051L); // Critical diastolic pressure
        PatientRecord record3 = new PatientRecord(1, 88.0, "Systolic", 1714376789052L);  // Normal systolic pressure
        PatientRecord record4 = new PatientRecord(1, 62.0, "Diastolic", 1714376789053L);  // Normal diastolic pressure
        List<PatientRecord> records = new ArrayList<>();
        records.add(record1);
        records.add(record2);
        records.add(record3);
        records.add(record4);

        BloodPressureAlert alert = new BloodPressureAlert();
        alert.evaluateAlert("patient123", records);

    }



}
