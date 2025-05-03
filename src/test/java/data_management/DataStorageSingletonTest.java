package data_management;

import com.data_management.DataStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public class DataStorageSingletonTest {

    @Test
    void testSingletonInstance() {
        DataStorage instance1 = DataStorage.getInstance();
        DataStorage instance2 = DataStorage.getInstance();

        assertSame(instance1, instance2, "DataStorage should be a singleton and return the same instance");
        instance1.addPatientData(1, 100.0, "HeartRate", 1714376789001L);
        assertFalse(instance2.getAllRecords(1).isEmpty(), "Data should be shared across both references");
    }
}