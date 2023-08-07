package com.github.tatercertified.minecraft.servers;

import com.github.tatercertified.minecraft.ServerType;
import com.github.tatercertified.minecraft.interfaces.AbstractModdedServerInterface;

public class ProxyServer extends ModdedServer implements AbstractModdedServerInterface {
    public ProxyServer(String path) {
        super(path);
    }

    @Override
    public ServerType getServerType() {
        return ServerType.PROXY;
    }

    @Override
    public String getBackupPath() {
        return null;
    }

    @Override
    public String getWorldPath() {
        return null;
    }

    @Override
    public void setWorldPath(String path) {
    }
}
