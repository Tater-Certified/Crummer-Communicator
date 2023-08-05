package com.github.tatercertified.networking;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.*;
import java.nio.ByteBuffer;

public class DataServer extends WebSocketServer {


    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Crummer Communicator Websocket Opened");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Crummer Communicator connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message Received: " + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        super.onMessage(conn, message);
        deserializeData(message.array());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Crummer Communicator Starting");
    }

    /**
     * Sends Data to a specific client
     * @param dataType DataType
     * @param extraData the actual data being sent to the client
     * @param socket The WebSocket that is receiving the data
     */
    public void sendData(DataType dataType, Object extraData, WebSocket socket) {
        byte[] data;

        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOut);

            out.writeShort(dataType.ordinal());

            out.writeObject(extraData);

            out.close();

            data = byteArrayOut.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        socket.send(data);
    }

    /**
     * Turns received data into Objects that can be used by the bot
     * @param data Incoming packet data
     */
    public void deserializeData(byte[] data) {
        Object dataObject;

        try {
            ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(data);
            ObjectInputStream in = new ObjectInputStream(byteArrayIn);

            short dataTypeValue = in.readShort();
            DataType dataType = DataType.values()[dataTypeValue];

            dataObject = in.readObject();

            in.close();

            switch (dataType) {
                case SERVER_TYPE ->
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
