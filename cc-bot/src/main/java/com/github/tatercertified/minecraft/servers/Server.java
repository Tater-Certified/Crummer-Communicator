package com.github.tatercertified.minecraft.servers;

import com.github.tatercertified.minecraft.ServerType;
import com.github.tatercertified.minecraft.interfaces.AbstractVanillaServerInterface;
import com.google.gson.annotations.Expose;
import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.List;

public class Server implements AbstractVanillaServerInterface {
    private String name;
    private final String path;
    private final List<String> supportedVersions = new ArrayList<>();
    private int port;
    private String domain;
    private String worldPath;
    @Expose
    private boolean running;
    @Expose
    private final String backupPath;
    @Expose
    private int pid;
    @Expose
    private final WebSocket socket;
    public Server(String path, WebSocket socket) {
        this.path = path;
        this.backupPath = path + "/backup";
        this.socket = socket;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public ServerType getServerType() {
        return ServerType.VANILLA;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getDomainName() {
        return this.domain;
    }

    @Override
    public void setDomainName(String domainName) {
        this.domain = domainName;
    }

    @Override
    public String getBackupPath() {
        return this.backupPath;
    }

    @Override
    public String getWorldPath() {
        return this.worldPath;
    }

    @Override
    public void setWorldPath(String path) {
        this.worldPath = path;
    }

    @Override
    public List<String> getSupportedVersions() {
        return this.supportedVersions;
    }

    @Override
    public void replaceSupportedVersions(List<String> newSupportedVersions) {
        this.supportedVersions.clear();
        this.supportedVersions.addAll(newSupportedVersions);
    }

    @Override
    public boolean getRunning() {
        return this.running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public int getPID() {
        return this.pid;
    }

    @Override
    public void setPID(int pid) {
        this.pid = pid;
    }
}
