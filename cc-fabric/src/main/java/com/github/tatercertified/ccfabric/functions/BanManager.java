package com.github.tatercertified.ccfabric.functions;

import com.github.tatercertified.ccfabric.compat.ModCompatibilityChecker;
import net.minecraft.server.MinecraftServer;

public class BanManager {
    public BanManager(MinecraftServer server) {
        if (ModCompatibilityChecker.banHammer) {
            // TODO Implement BanHammer banning with specified time
        } else {
            // TODO Implement Vanilla banning
        }
    }
}
