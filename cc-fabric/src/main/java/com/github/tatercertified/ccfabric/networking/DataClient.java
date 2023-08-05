package com.github.tatercertified.ccfabric.networking;

import com.github.tatercertified.ccfabric.compat.ModCompatibilityChecker;
import com.github.tatercertified.ccfabric.functions.BackupManager;
import com.github.tatercertified.ccfabric.functions.BanManager;
import com.github.tatercertified.ccfabric.functions.ProfilerManager;
import gs.mclo.java.APIResponse;
import gs.mclo.java.MclogsAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

public class DataClient extends WebSocketClient {

    private MinecraftServer server;

    public DataClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handShakeData) {
        System.out.println("Crummer Communicator Ready!");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received Data: " + message);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
        deserializeData(bytes.array());
    }



    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Crummer Communicator connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void sendData(DataType dataType, Object additionalData) {
        byte[] data;

        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOut);

            out.writeShort(dataType.ordinal());

            out.writeObject(additionalData);

            out.close();

            data = byteArrayOut.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.send(data);
    }

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
                case SERVER_TYPE -> sendData(DataType.SERVER_TYPE, "fabric");
                case SERVER_STOP -> System.exit(0);
                case SERVER_RESTART -> {
                    if ((boolean) dataObject) {
                        // Quick reboot
                        // TODO TEST
                        server.stop(true);
                        MinecraftServer.startServer(thread -> null);
                    } else {
                        // TODO Hack into exit() to send start code back to Discord Bot
                        System.exit(0);
                    }
                }
                case SERVER_KILL -> Runtime.getRuntime().halt(0);
                case SEND_MESSAGE -> server.sendMessage(Text.literal((String)dataObject));
                case PROFILE -> new ProfilerManager(server);
                case COMMAND_EXECUTED -> {
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder((String)dataObject);
                        processBuilder.start();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case UPLOAD_LOG -> sendData(DataType.UPLOAD_LOG, readLatestLog());
                case WHITELIST -> {
                    if (ModCompatibilityChecker.fabricProxyLite) {
                        // Use Velocity plugin instead
                        sendData(DataType.FAILURE, "fabric-proxy-lite");
                    } else {
                        // TODO Implement vanilla whitelisting
                    }
                }
                case BAN -> new BanManager(server);
                case BACKUP -> new BackupManager();
                case ONLINE_PLAYERS -> sendData(DataType.ONLINE_PLAYERS, server.getPlayerNames());
                case PORT -> sendData(DataType.PORT, server.getServerPort());
                case IP -> sendData(DataType.IP, server.getServerIp());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public enum DataType {
        // Networking Data
        SERVER_TYPE,
        FAILURE,
        SUCCESS,
        // Server Control
        SERVER_START,
        SERVER_STOP,
        SERVER_RESTART,
        SERVER_KILL,
        // Server Events
        SERVER_STARTED,
        SERVER_LAGGING,
        SERVER_CRASH,
        PLAYER_JOIN,
        PLAYER_LEAVE,
        SERVER_NETWORKING_OFFLINE,
        SEND_MESSAGE,
        PROFILE,
        // Commands
        COMMAND_EXECUTED,
        // Server Management
        UPLOAD_LOG,
        WHITELIST,
        BAN,
        BACKUP,
        // Informative
        ONLINE_PLAYERS,
        PORT,
        IP
    }

    public void setMinecraftServer(MinecraftServer server) {
        this.server = server;
    }

    @Nullable
    public String readLatestLog() {
        final File latest = new File(new File(server.getRunDirectory(), "logs"), "latest.log");
        String url;
        try {
            APIResponse mclogs = MclogsAPI.share(Paths.get(latest.getPath()));

            if (!mclogs.success || mclogs.id == null || mclogs.url == null || mclogs.error != null) {
                sendData(DataType.FAILURE, "mclogs-fail");
                return null;
            }

            url = mclogs.url;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return url;
    }
}