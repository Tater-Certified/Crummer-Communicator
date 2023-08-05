package com.github.tatercertified;

import com.github.tatercertified.config.Config;
import com.github.tatercertified.config.ConfigManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
    public static Config config;

    public static void main(String[] args) {
        System.out.println("Welcome to Crummer Communicator Bot version 1.0.0!");
        config = ConfigManager.loadConfig();

        JDABuilder builder = JDABuilder.createDefault(config.token);
        builder.disableCache(CacheFlag.VOICE_STATE, CacheFlag.STICKER, CacheFlag.SCHEDULED_EVENTS, CacheFlag.EMOJI);
        builder.setLargeThreshold(50);
        builder.build();
    }

}