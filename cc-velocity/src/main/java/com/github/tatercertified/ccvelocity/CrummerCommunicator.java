package com.github.tatercertified.ccvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(
        id = "cc-velocity",
        name = "Crummer Communicator",
        version = BuildConstants.VERSION,
        description = "Allows communicators between the server and discord",
        authors = {"QPCrummer"}
)
public class CrummerCommunicator {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
