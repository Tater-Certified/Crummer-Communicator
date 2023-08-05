package com.github.tatercertified.minecraft;

import com.google.gson.annotations.Expose;

import java.nio.file.Path;

public class BasicServer {
    public String name;
    public final String path;
    public String serverType;
    public String[] supportedVersions;
    public int port;
    public String domain;
    public String worldPath;
    @Expose
    public boolean running;
    @Expose
    public final Path backupPath;
    @Expose
    public int pid;
    public BasicServer(String path) {
        this.path = path;
        this.backupPath = Path.of(path + "/backup");
    }
}
