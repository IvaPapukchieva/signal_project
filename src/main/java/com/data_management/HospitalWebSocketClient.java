package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class HospitalWebSocketClient extends WebSocketClient implements DataReader{

    DataStorage dataStorage;
    // Constructor takes a URI (e.g. ws://localhost:8887)
    public HospitalWebSocketClient (URI serverUri) {
        super(serverUri);
        dataStorage=DataStorage.getInstance();

    }

    // Called when the connection is opened
    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to server");
        send("Hello from client!");
    }

    // Called when a message is received
    @Override
    public void onMessage(String message) {
        System.out.println("Server says: " + message);
    }

    // Called when connection is closed
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected");
    }


    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        URI serverUri = new URI("ws://localhost:8887");
        HospitalWebSocketClient client = new HospitalWebSocketClient (serverUri);
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


        client.connectBlocking();
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {

    }
}
