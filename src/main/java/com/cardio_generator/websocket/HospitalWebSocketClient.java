package com.cardio_generator.websocket;
import com.data_management.DataStorage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.util.Scanner;

/**
 * HospitalWebSocketClient connects to a WebSocket server to receive real-time patient monitoring data.
 * It parses incoming messages and stores the data using the DataStorage singleton.
 *
 * <p>Expected message format: "patientId,timestamp,label,value"</p>
 * <p>Example: "101,1716452084567,SPO2,97%"</p>
 *
 * This class extends {@link WebSocketClient} from the org.java_websocket library.
 */
public class HospitalWebSocketClient extends WebSocketClient {

    private final DataStorage dataStorage;

    /**
     * Constructs a new HospitalWebSocketClient with the given server URI.
     *
     * @param serverUri the URI of the WebSocket server (e.g. ws://localhost:8887)
     */
    public HospitalWebSocketClient(URI serverUri) {
        super(serverUri);
        this.dataStorage = DataStorage.getInstance();
    }

    /**
     * Called when the WebSocket connection is successfully opened.
     *
     * @param handshake the server handshake data
     */
    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to server");
    }

    /**
     * Called when a message is received from the server.
     * Parses the message and stores the extracted data.
     *
     * @param message the raw message string in the format "patientId,timestamp,label,value"
     */
    @Override
    public void onMessage(String message) {
        System.out.println("Received: " + message);

        if (!message.matches("\\d+,\\d+,[^,]+,[^,]+")) {
            System.out.println("Skipping non-data message: " + message);
            return;
        }

        try {
            String[] parts = message.split(",", 4);

            int patientId = Integer.parseInt(parts[0].trim());
            long timestamp = Long.parseLong(parts[1].trim());
            String label = parts[2].trim();
            String rawValue = parts[3].trim().replace("%", "");
            double measurementValue = Double.parseDouble(rawValue);

            dataStorage.addPatientData(patientId, measurementValue, label, timestamp);

        } catch (Exception e) {
            System.err.println("Error parsing message: " + message);
            e.printStackTrace();
        }
    }

    /**
     * Called when the WebSocket connection is closed.
     *
     * @param code the status code for the closure
     * @param reason the reason for closure
     * @param remote true if closed remotely by the server
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("The WebSocket connection is closed. Reason: " + reason);
    }

    /**
     * Called when an error occurs during WebSocket communication.
     *
     * @param ex the exception that occurred
     */
    @Override
    public void onError(Exception ex) {
        System.out.println("WebSocket error occurred");
        ex.printStackTrace();
    }

    /**
     * Main method to start the client and allow interactive message sending.
     * Also connects to the server and keeps listening until "exit" is typed.
     *
     * @param args command-line arguments (not used)
     * @throws Exception if connection or URI initialization fails
     */
    public static void main(String[] args) throws Exception {
        URI serverUri = new URI("ws://localhost:8887");
        HospitalWebSocketClient client = new HospitalWebSocketClient(serverUri);
        client.connectBlocking();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a message to send. Type 'exit' to quit.");

        while (true) {
            String message = scanner.nextLine();
            if ("exit".equalsIgnoreCase(message)) {
                break;
            }
            client.send(message);
        }

        client.close();
    }
}