package com.github.tatercertified.minecraft.interfaces;

import com.github.tatercertified.minecraft.ServerType;
import org.java_websocket.WebSocket;

import java.util.List;

public interface AbstractVanillaServerInterface {
    // Info

    /**
     * Used to retrieve the name of the Server
     * @return Name as a String
     */
    String getName();
    void setName(String name);

    /**
     * Used to retrieve the path of the Server. This is the main directory that contains the start.sh file!
     * @return Path as a String
     */
    String getPath();

    /**
     * Used to retrieve the ServerType of the Server. This is for differentiating between server functions.
     * - Vanilla (Default): Contains basic features for a standard Server
     * - Modded (Forge/Fabric/Bukkit): Use the included mod to gain more advanced features that are tightly integrated with the Server's code
     * - Proxy (Velocity/Bungeecord): A cut-down version of Modded for use with proxy software
     * @return ServerType as a ServerType Object
     */
    ServerType getServerType();

    /**
     * Used to retrieve the port of the Server.
     * It will search the config files for the server to find the port by default. A fallback solution is yet to be implemented
     * @return Port as an Integer
     */
    int getPort();
    void setPort(int port);

    /**
     * Used to retrieve the IP address or domain name (if present).
     * It will search the config files for the server to find the IP by default. A fallback solution is yet to be implemented
     * @return Domain Name/IP as a String
     */
    String getDomainName();
    void setDomainName(String domainName);

    /**
     * Used to retrieve the Path to the Server's world backup
     * @return Backup Path as a String
     */
    String getBackupPath();

    /**
     * Used to retrieve the Path to the Server's world
     * @return World Path as String
     */
    String getWorldPath();
    void setWorldPath(String path);

    /**
     * Uses to retrieve a List of supported Minecraft versions.
     * Because a new List Object is not created, you can use this method to get the List, and then add a String to it for a new version
     * @return List of supported Minecraft versions as a List of Strings
     */
    List<String> getSupportedVersions();
    void replaceSupportedVersions(List<String> newSupportedVersions);

    // Operating

    /**
     * Used to check if the Server is online.
     * If the servers are tampered with by outside influences, the bot will attempt to locate the Server (WIP)
     * @return if running as a Boolean
     */
    boolean getRunning();
    void setRunning(boolean running);

    /**
     * Used to retrieve the PID number for the Server's JVM.
     * This will be used to destroy the JVM if it stalls
     * @return PID as an Integer
     */
    int getPID();
    void setPID(int pid);

    /**
     * Used to retrieve the Websocket of a Server
     * @return Websocket of the Server
     */
    WebSocket getWebsocket();
}
