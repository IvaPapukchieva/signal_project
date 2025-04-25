package data_management;

import com.alerts.alert_strategies.BloodPressureStrategy;
import com.alerts.alert_strategies.HeartRateStrategy;
import com.alerts.alert_strategies.HypotensiveHypoxemiaStrategy;
import com.alerts.alert_strategies.OxygenSaturationStrategy;
import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataStorageTest {

//    test adding and getting records
    @Test
    void testAddAndGetRecords() {

        DataStorage dataStorage= DataStorage.getInstance();

        dataStorage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        dataStorage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = dataStorage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
    }

//    test the fileDataReader
    @Test
    void testFileDataReader() throws IOException {
        FileWriter writer = new FileWriter("test_fileDataReader.txt");
        writer.write("patientId,timestamp,recordType,value\n");
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

        HypotensiveHypoxemiaStrategy alert = new  HypotensiveHypoxemiaStrategy ();
        alert.checkAlert("1", records);

    }

    @Test
    void testOxygenSaturationAlert() {

        PatientRecord record1 = new PatientRecord(1, 88.0, "Oxygen Saturation", 1714376789050L);
        PatientRecord record2 = new PatientRecord(1, 75.0, "Oxygen Saturation", 1714376789051L);
        PatientRecord record3 = new PatientRecord(1, 95.0, "Oxygen Saturation", 1714376789052L);
        List<PatientRecord> records = new ArrayList<>();
        records.add(record1);
        records.add(record2);
        records.add(record3);
        OxygenSaturationStrategy alert = new  OxygenSaturationStrategy();
        alert.checkAlert("1", records);


    }

    @Test
    void testECGAlert() {
        PatientRecord record1 = new PatientRecord(1, 110.0, "ECG", 1714376789050L); // Abnormal ECG
        PatientRecord record2 = new PatientRecord(1, 90.0, "ECG", 1714376789051L);  // Normal ECG
        List<PatientRecord> records = new ArrayList<>();
        records.add(record1);
        records.add(record2);

        HeartRateStrategy alert = new HeartRateStrategy();
        alert.checkAlert("1", records);

    }

    @Test
    void testBloodPressureAlert() {
        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 170.0, "Systolic", 1714376789001L));
        records.add(new PatientRecord(1, 105.0, "Systolic", 1714376789002L));
        records.add(new PatientRecord(1, 94.0, "Systolic", 1714376789003L));
        records.add(new PatientRecord(1, 1000.0, "Systolic", 1714376789001L));
        records.add(new PatientRecord(1, 97.0, "Diastolic", 1714376789001L));
        records.add(new PatientRecord(1, 109.0, "Diastolic", 1714376789002L));
        records.add(new PatientRecord(1, 120.0, "Diastolic", 1714376789003L));
        records.add(new PatientRecord(1, 1000.0, "Diastolic", 1714376789001L));



        BloodPressureStrategy alert = new  BloodPressureStrategy();
        alert.checkAlert("1", records);
    }


}
