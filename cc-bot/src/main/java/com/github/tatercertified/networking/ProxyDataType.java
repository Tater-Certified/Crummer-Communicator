package com.github.tatercertified.networking;

public enum ProxyDataType {
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
    SERVER_CRASH,
    PROFILE,
    // Commands
    COMMAND_EXECUTED,
    // Server Management
    UPLOAD_LOG,
    WHITELIST,
    // Informative
    ONLINE_PLAYERS,
    PORT,
    IP
}
