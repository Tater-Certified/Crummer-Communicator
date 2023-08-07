package com.github.tatercertified.minecraft.servers;

import com.github.tatercertified.minecraft.ServerType;
import com.github.tatercertified.minecraft.interfaces.AbstractModdedServerInterface;

import java.util.ArrayList;
import java.util.List;

public class ModdedServer extends Server implements AbstractModdedServerInterface {
    private final List<String> mods = new ArrayList<>();
    public ModdedServer(String path) {
        super(path);
    }

    @Override
    public List<String> getMods() {
        return this.mods;
    }

    @Override
    public void replaceMods(List<String> newMods) {
        this.mods.clear();
        this.mods.addAll(newMods);
    }

    @Override
    public ServerType getServerType() {
        return ServerType.MODDED;
    }
}
