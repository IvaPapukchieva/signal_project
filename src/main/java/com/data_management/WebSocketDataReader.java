package com.data_management;

import java.io.IOException;
import java.net.URI;

import com.cardio_generator.websocket.HospitalWebSocketClient;

public class WebSocketDataReader implements DataReader {

    private final String websocketUrl;

    /**
     * Constructs a new WebSocketDataReader with the specified WebSocket server URL.
     *
     * @param websocketUrl the URL of the WebSocket server
     */
    public WebSocketDataReader(String websocketUrl) {
        this.websocketUrl = websocketUrl;
    }

    /**
     * Connects to the WebSocket server and starts receiving data.
     * Received data is passed to a {@link HospitalWebSocketClient}, which adds it to the provided {@link DataStorage} instance.
     *
     * @param dataStorage the {@link DataStorage} instance to populate with incoming data
     * @throws IOException if there is a failure connecting to the WebSocket server or processing the URI
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try {
            URI uri = new URI(websocketUrl);
            HospitalWebSocketClient hospitalWebSocketClient = new  HospitalWebSocketClient (uri);
            hospitalWebSocketClient.connectBlocking();
        } catch (Exception e) {
            throw new IOException("Failed to connect to WebSocket server", e);
        }
    }
}