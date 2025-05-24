package websocket_tests;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.junit.jupiter.api.*;
import com.data_management.WebSocketDataReader;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketErrorHandlingTest {

    private static final int PORT = 8896;
    private TestWebSocketServer server;

    static class TestWebSocketServer extends WebSocketServer {
        private WebSocket webSocket;
        private final String scenario;

        public TestWebSocketServer(int port, String scenario) {
            super(new InetSocketAddress(port));
            this.scenario = scenario;
        }

        @Override
        public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
            this.webSocket = webSocket;

            switch (scenario) {
                case "MALFORMED":
                    webSocket.send("INVALID_DATA_NO_COMMAS");
                    break;
                case "DISCONNECT":
                    webSocket.send("3,1700001234567,HeartRate,100");
                    webSocket.close();
                    break;
                case "LATENCY":
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            webSocket.send("3,1700001234567,ECG,1.5");
                        } catch (InterruptedException ignored) {}
                    }).start();
                    break;
                case "NORMAL":
                    webSocket.send("3,1700001234567,BloodSaturation,92");
                    break;
            }
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

    @BeforeEach
    public void clearData() {
        DataStorage.getInstance().clear();
    }

    @AfterEach
    public void stopServer() throws IOException, InterruptedException {
        if (server != null) {
            server.shutdown();
        }
    }

    @Test
    public void testMalformedDataHandling() throws Exception {
        server = new TestWebSocketServer(PORT, "MALFORMED");
        server.start();
        Thread.sleep(500);

        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:" + PORT);
        assertDoesNotThrow(() -> reader.readData(DataStorage.getInstance()));

        Thread.sleep(1000);

        List<PatientRecord> records = DataStorage.getInstance().getAllRecords(0);
        assertTrue(records.isEmpty(), "Malformed data should not be stored");
    }

    @Test
    public void testServerDisconnectHandling() throws Exception {
        server = new TestWebSocketServer(PORT, "DISCONNECT");
        server.start();
        Thread.sleep(500);

        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:" + PORT);
        assertDoesNotThrow(() -> reader.readData(DataStorage.getInstance()));

        Thread.sleep(1000);

        List<PatientRecord> records = DataStorage.getInstance().getRecords(3, 0, Long.MAX_VALUE);
        assertFalse(records.isEmpty(), "Partial data before disconnect should still be stored");
    }

    @Test
    public void testHighLatencyHandling() throws Exception {
        server = new TestWebSocketServer(PORT, "LATENCY");
        server.start();
        Thread.sleep(500);

        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:" + PORT);
        reader.readData(DataStorage.getInstance());

        Thread.sleep(2500);

        List<PatientRecord> records = DataStorage.getInstance().getRecords(3, 0, Long.MAX_VALUE);
        assertFalse(records.isEmpty(), "Data should be correctly received even after delay");
    }

}