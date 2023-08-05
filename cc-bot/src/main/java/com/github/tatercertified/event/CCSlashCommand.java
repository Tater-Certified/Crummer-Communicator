package com.github.tatercertified.event;

import com.github.tatercertified.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

public class CCSlashCommand extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        JDA jda = event.getJDA();
        TextChannel botChannel = jda.getChannelById(TextChannel.class, Main.config.botChannel);
        if (botChannel == null) {
            try {
                throw new Exception("Bot Channel is invalid");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        botChannel.deleteMessageById(botChannel.getLatestMessageIdLong());

        botChannel.sendMessage("Choose a Server")
                .addActionRow(
                        StringSelectMenu.create("server-list")
                )
    }
}
