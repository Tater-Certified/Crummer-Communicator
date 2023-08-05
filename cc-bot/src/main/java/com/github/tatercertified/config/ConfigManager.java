package com.github.tatercertified.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    /**
     * Creates the necessary files
     */
    private static void loadPaths() {
        Path path = Path.of("config.json");
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            saveConfig(setDefaults());
        }
    }

    /**
     * Converts the json to a Class
     * @return Config Class
     */
    public static Config loadConfig() {
        loadPaths();
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        try (BufferedReader reader = new BufferedReader(new FileReader("config.json"))) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the Config Class to a json
     * @param config Config Class
     */
    public static void saveConfig(Config config) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        String json = gson.toJson(config);
        writeJson(json);
    }

    /**
     * Writes a Config Class to a File
     * @param json String after gson.toJson()
     */
    private static void writeJson(String json) {
        try (FileWriter writer = new FileWriter("config.json")) {
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets defaults for a Config Class
     * @return Config Class
     */
    private static Config setDefaults() {
        Config config = new Config();
        config.token = "change-me-to-your-bot-token";
        config.botChannel = "bot-channel-id";
        config.consoleChannel = "console-channel-id";
        return config;
    }
}
