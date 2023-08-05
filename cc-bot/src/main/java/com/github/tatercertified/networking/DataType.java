package com.github.tatercertified.networking;

public enum DataType {
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
    SERVER_LAGGING,
    SERVER_CRASH,
    PLAYER_JOIN,
    PLAYER_LEAVE,
    SEND_MESSAGE,
    PROFILE,
    // Commands
    COMMAND_EXECUTED,
    // Server Management
    UPLOAD_LOG,
    WHITELIST,
    BAN,
    BACKUP,
    // Informative
    ONLINE_PLAYERS,
    PORT,
    IP
}
