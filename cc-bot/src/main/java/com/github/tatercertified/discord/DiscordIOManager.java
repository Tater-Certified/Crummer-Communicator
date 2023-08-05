package com.github.tatercertified.discord;

import com.github.tatercertified.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiscordIOManager {
    private final TextChannel commandChannel;
    private final TextChannel consoleChannel;
    public DiscordIOManager(JDA jda) {
        this.commandChannel = jda.getChannelById(TextChannel.class, Main.config.botChannel);
        this.consoleChannel = jda.getChannelById(TextChannel.class, Main.config.consoleChannel);
    }

    public void sendMessage(String message, TextChannel channel) {
        channel.sendMessage(message);
    }

    public void removeMessage(long messageID, TextChannel channel) {
        channel.deleteMessageById(messageID);
    }

    /**
     * Sends a message to the console
     * @param message String that contains what you want to be outputted in the console channel
     * @param type MessageType; How it should be formatted
     */
    public void outputToConsole(String message, MessageType type) {
        String finalOutput = null;
        switch (type) {
            case STANDARD -> finalOutput = "`" + message + "`";
            case ERROR -> finalOutput = "```diff\n- " + message + "\n```";
            case WARN -> finalOutput = "```css\n[" + message + "]\n```";
            case LINK -> finalOutput = "<" + message + ">";
            case UNFORMATTED -> finalOutput = message;
            case HYPERLINK -> {
                String[] components = separateHyperlink(message);
                MessageEmbed embed = new EmbedBuilder().addField("text", components[0], true).setUrl(components[1]).build();
                consoleChannel.sendMessageEmbeds(embed);
            }
        }
        if (finalOutput != null) {
            sendMessage(finalOutput, consoleChannel);
        }
    }

    public void createGUI() {

    }

    /**
     * Standard: `message`
     * Error: ```diff - message```
     * Warn: ```css [message]```
     * HyperLink: [message](link)
     * Link: <message>
     * Unformatted: message
     */
    public enum MessageType {
        STANDARD,
        ERROR,
        WARN,
        HYPERLINK,
        LINK,
        UNFORMATTED,
    }

    /**
     * Separates the text and url
     * @param hyperlink Must be in [message](link) format!
     * @return String array. 0 = message, 1 = link
     */
    private String[] separateHyperlink(String hyperlink) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(hyperlink);

        if (matcher.find()) {
            String message = matcher.group(1);
            String link = matcher.group(2);
            return new String[]{message, link};
        }
        return null;
    }
}
