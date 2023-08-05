package com.github.tatercertified.ccfabric.functions;

import com.github.tatercertified.ccfabric.compat.ModCompatibilityChecker;
import net.minecraft.server.MinecraftServer;

public class ProfilerManager {

    public ProfilerManager(MinecraftServer server) {
        if (ModCompatibilityChecker.spark) {
            // TODO Add Spark Support
        } else if (ModCompatibilityChecker.carpet) {
            // TODO Add Carpet Support
        } else {
            // TODO Mixin into startTick() so that it runs for a set amount of time, and then sends the data back to the Discord Bot
            server.getProfiler().startTick();
        }
    }
}
