package websocket_tests;

import com.cardio_generator.websocket.HospitalWebSocketClient;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.junit.jupiter.api.*;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class HospitalWebSocketClientTest {

    private static final int TEST_PORT = 8891;
    private static TestWebSocketServer server;
    private HospitalWebSocketClient client;
    private DataStorage dataStorage;

    @BeforeAll
    static void startServer() throws Exception {
        server = new TestWebSocketServer(new InetSocketAddress(TEST_PORT));
        server.start();
        Thread.sleep(500); // Give server time to start
    }

    @AfterAll
    static void stopServer() throws Exception {
        server.stop(1000);
    }

    @BeforeEach
    void setUp() throws Exception {
        dataStorage = DataStorage.getInstance();
        dataStorage.clear(); // Clear previous test data if you have this method
        URI uri = new URI("ws://localhost:" + TEST_PORT);
        client = new HospitalWebSocketClient(uri);
        client.connectBlocking();
    }

    @AfterEach
    void tearDown() throws Exception {
        client.closeBlocking();
    }

    @Test
    void testValidMessageIsParsedAndStored() throws Exception {
        String validMessage = "7,1747209839355,Glucose,105.6";
        server.broadcast(validMessage);
        TimeUnit.MILLISECONDS.sleep(300);

        List<PatientRecord> records = dataStorage.getRecords(7, 0, Long.MAX_VALUE);
        assertFalse(records.isEmpty(), "Expected data storage to contain a record");
        PatientRecord record = records.get(0);
        assertEquals("Glucose", record.getRecordType());
        assertEquals(105.6, record.getMeasurementValue(), 0.01);
    }

    @Test
    void testInvalidMessageIsIgnored() throws Exception {
        String badMessage = "bad format ";
        server.broadcast(badMessage);
        TimeUnit.MILLISECONDS.sleep(300);

        assertTrue(dataStorage.getRecords(999, 0, Long.MAX_VALUE).isEmpty());
    }

    @Test
    void testMessageWithPercentageSymbol() throws Exception {
        String message = "4,1747209839999,SPO2,98%";
        server.broadcast(message);
        TimeUnit.MILLISECONDS.sleep(300);

        List<PatientRecord> records = dataStorage.getRecords(4, 0, Long.MAX_VALUE);
        assertFalse(records.isEmpty(), "Expected a record for patient 4");
        assertEquals(98.0, records.get(0).getMeasurementValue(), 0.01);
    }

    static class TestWebSocketServer extends WebSocketServer {
        public TestWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            System.out.println("Test server: client connected");
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
            System.out.println("Test WebSocket server running on port " + getPort());
        }
    }
}