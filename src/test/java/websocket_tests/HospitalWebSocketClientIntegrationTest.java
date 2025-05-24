package websocket_tests;


import com.alerts.AlertGenerator;
import com.data_management.*;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalWebSocketClientIntegrationTest {

    private static final int PORT = 8898;
    private static TestWebSocketServer server;

    static class TestWebSocketServer extends WebSocketServer {
        private WebSocket webSocket;

        public TestWebSocketServer(int port) {
            super(new InetSocketAddress(port));
        }

        @Override
        public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
            this.webSocket = webSocket;
            webSocket.send("0,1700001234567,BloodPressureSystolic,140");
        }

        @Override
        public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {}

        @Override
        public void onMessage(WebSocket webSocket, String message) {}

        @Override
        public void onError(WebSocket webSocket, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Test WebSocket server started on port " + getPort());
        }

        public void shutdown() throws IOException, InterruptedException {
            this.stop(1000);
        }
    }

    @BeforeAll
    public static void startServer() throws Exception {
        server = new TestWebSocketServer(PORT);
        server.start();
        Thread.sleep(500); // Give the server time to bind
    }

    @AfterAll
    public static void stopServer() throws IOException, InterruptedException {
        server.shutdown();
    }

    @BeforeEach
    public void clearData() {
        DataStorage.getInstance().clear();
    }

    @Test
    public void testWebSocketClientReceivesAndStoresData() throws Exception {
        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:" + PORT);
        reader.readData(DataStorage.getInstance());

        Thread.sleep(1500); // Allow time to receive/process

        List<PatientRecord> records = DataStorage.getInstance().getRecords(0, 0, Long.MAX_VALUE);
        assertFalse(records.isEmpty(), "Patient data should be stored.");

        PatientRecord record = records.get(0);
        assertEquals(0, record.getPatientId());
        assertEquals("BloodPressureSystolic", record.getRecordType());
        assertEquals(140.0, record.getMeasurementValue(), 0.01);
        assertEquals(1700001234567L, record.getTimestamp());
    }

    @Test
    public void testAlertGenerationFromStoredData() throws Exception {
        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:" + PORT);
        reader.readData(DataStorage.getInstance());

        Thread.sleep(1500); // Wait for client to receive and process data

        Patient patient = DataStorage.getInstance().getAllPatients().get(0);
        AlertGenerator generator = new AlertGenerator();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        patient.addRecord(190, "ECG",  System.currentTimeMillis());
        generator.evaluateData(patient);

        patient.addRecord(85.0, "Saturation", System.currentTimeMillis());
        generator.evaluateData(patient);

        patient.addRecord(200, "SystolicPressure", System.currentTimeMillis());
        generator.evaluateData(patient);
        System.setOut(System.out);

        String output = out.toString();
        assertTrue(output.contains("Alert:"), "Expected alert output to be present.");
        assertTrue(output.contains("Blood Pressure ")||output.contains("Heart Rate")|| output.contains("Blood Oxygen"));
    }
}