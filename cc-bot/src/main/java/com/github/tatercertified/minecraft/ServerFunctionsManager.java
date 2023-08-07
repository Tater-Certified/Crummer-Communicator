package com.github.tatercertified.minecraft;

import com.github.tatercertified.discord.DiscordIOManager;
import com.github.tatercertified.minecraft.servers.Server;
import com.github.tatercertified.networking.DataServer;
import com.github.tatercertified.networking.DataType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

public class ServerFunctionsManager {

    private final DataServer dataServer;

    public ServerFunctionsManager(DataServer dataServer) {
        this.dataServer = dataServer;
    }

    /**
     * Copies the world folder and runs it through a GZip compressor
     * @param dioManager DiscordIOManager class
     * @param server Server class
     */
    public void backupWorld(Server server, DiscordIOManager dioManager) {
        Path backupPath = Path.of(server.getBackupPath());
        String worldPathString = server.getWorldPath();
        if (Files.notExists(backupPath)) {
            try {
                Files.createDirectory(backupPath);
            } catch (IOException e) {
                dioManager.outputToConsole("Error: Failed to create Backup folder for " + server.getName(), DiscordIOManager.MessageType.ERROR);
                throw new RuntimeException(e);
            }
        }

        Path destinationPath;
        try {
            Path worldPath = Paths.get(worldPathString);
            destinationPath = Paths.get(backupPath + "/world_backup-" + Date.from(Instant.now()));
            Path finalDestinationPath = destinationPath;
            Files.walk(worldPath)
                    .forEach(source -> {
                        try {
                            Path target = finalDestinationPath.resolve(worldPath.relativize(source));
                            Files.copy(source, target, StandardCopyOption.COPY_ATTRIBUTES);
                        } catch (IOException e) {
                            dioManager.outputToConsole("Error: Failed to copy world folder as backup for " + server.getName(), DiscordIOManager.MessageType.ERROR);
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String compressedFilePath = destinationPath + ".gz";
        try (FileOutputStream fos = new FileOutputStream(compressedFilePath);
             GZIPOutputStream gzipOS = new GZIPOutputStream(fos)) {

            Files.walk(destinationPath)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            byte[] buffer = new byte[1024];
                            FileInputStream fis = new FileInputStream(file.toFile());
                            int len;
                            while ((len = fis.read(buffer)) != -1) {
                                gzipOS.write(buffer, 0, len);
                            }
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            dioManager.outputToConsole("Error: Failed to compress backup for " + server.getName(), DiscordIOManager.MessageType.ERROR);
            e.printStackTrace();
            return;
        }
        dioManager.outputToConsole("Successfully created a backup for " + server.getName(), DiscordIOManager.MessageType.STANDARD);
    }

    /**
     * Starts a Server from its start.sh file
     * @param server Server class
     * @param dioManager DiscordIOManager class
     */
    public void startServer(Server server, DiscordIOManager dioManager) {
        String startFilePath = server.getPath() + "/start.sh";
        if (Files.notExists(Path.of(startFilePath))) {
            dioManager.outputToConsole("Error: start.sh file not found for " + server.getName(), DiscordIOManager.MessageType.ERROR);
            return;
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", startFilePath);
            Process process = processBuilder.start();

            server.setPID(getProcessId(process));

        } catch (IOException e) {
            dioManager.outputToConsole("Error: Failed to start " + server.getName(), DiscordIOManager.MessageType.ERROR);
            e.printStackTrace();
        }
    }

    private int getProcessId(Process process) {
        try {
            Class<?> processImplClass = process.getClass();
            java.lang.reflect.Field f = processImplClass.getDeclaredField("pid");
            f.setAccessible(true);
            return (int) f.get(process);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Stops a Server.
     * The Discord Bot will receive a packet from the Server if it was shutdown successfully
     * @param server Server class
     */
    public void stopServer(Server server) {
        this.dataServer.sendData(DataType.SERVER_START, null, server.getWebsocket());
    }

    /**
     * Restarts a Server.
     * The Discord Bot will receive a packet from the Server if it was restarted successfully
     * @param server Server class
     * @param softRestart if the JVM should not restart
     */
    public void restartServer(Server server, boolean softRestart) {
        this.dataServer.sendData(DataType.SERVER_RESTART, softRestart, server.getWebsocket());
    }
}
