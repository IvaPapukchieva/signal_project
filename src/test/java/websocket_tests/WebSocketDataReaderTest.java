package websocket_tests;


import com.cardio_generator.websocket.HospitalWebSocketClient;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.WebSocketDataReader;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.junit.jupiter.api.*;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class WebSocketDataReaderTest {

    private static final int TEST_PORT = 8892;
    private static TestWebSocketServer server;
    private DataStorage dataStorage;

    @BeforeAll
    static void startServer() throws Exception {
        server = new TestWebSocketServer(new InetSocketAddress(TEST_PORT));
        server.start();
        Thread.sleep(500);
    }

    @AfterAll
    static void stopServer() throws Exception {
        if (server != null) {
            server.stop(1000);
        }
    }

    @BeforeEach
    void setUp() {
        dataStorage = DataStorage.getInstance();
        dataStorage.clear();
    }

    @Test
    void testReadDataReceivesAndStoresPatientData() throws Exception {
        // Given
        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:" + TEST_PORT);
        String testMessage = "5,1747209839999,Cholesterol,200.0";

        // When
        new Thread(() -> {
            try {
                reader.readData(dataStorage);
            } catch (Exception e) {
                fail("WebSocket connection failed: " + e.getMessage());
            }
        }).start();

        // Give time to connect and process
        Thread.sleep(500);
        server.broadcast(testMessage);
        Thread.sleep(500);

        // Then
        List<PatientRecord> records = dataStorage.getRecords(5, 0, Long.MAX_VALUE);
        assertFalse(records.isEmpty(), "Expected record for patient 5");
        PatientRecord record = records.get(0);
        assertEquals("Cholesterol", record.getRecordType());
        assertEquals(200.0, record.getMeasurementValue(), 0.01);
    }

    // Minimal WebSocket test server
    static class TestWebSocketServer extends WebSocketServer {
        public TestWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            System.out.println("Test server accepted connection");
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {}

        @Override
        public void onMessage(WebSocket conn, String message) {}

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Test WebSocket server started on port " + getPort());
        }
    }
}