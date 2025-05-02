package data_management;


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


}
