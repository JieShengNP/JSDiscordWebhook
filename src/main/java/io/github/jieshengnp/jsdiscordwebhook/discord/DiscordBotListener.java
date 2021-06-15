package io.github.jieshengnp.jsdiscordwebhook.discord;

import com.google.common.collect.ImmutableList;
import io.github.jieshengnp.jsdiscordwebhook.JSDiscordWebhook;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DiscordBotListener extends ListenerAdapter {

    private final JSDiscordWebhook JSDISCORDWEBHOOK;
    private final String PREFIX;
    private final String CHANNEL_NAME;

    public DiscordBotListener(JSDiscordWebhook jsDiscordWebhook, String prefix, String channelName){
        this.JSDISCORDWEBHOOK = jsDiscordWebhook;
        this.PREFIX = prefix;
        this.CHANNEL_NAME = channelName;
    }
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        if (!event.getAuthor().isBot()){
            if (event.getMessage().getContentRaw().startsWith(PREFIX)){
                String[] args = event.getMessage().getContentRaw().split("\\s+");
                if (event.getChannel().getName().equalsIgnoreCase(CHANNEL_NAME)){
                    if (args[0].equalsIgnoreCase(PREFIX + "list")){
                        event.getChannel().sendMessage(String.format("**[%d/%d] players online.**", JSDISCORDWEBHOOK.getServer().getOnlinePlayers().size(), JSDISCORDWEBHOOK.getServer().getMaxPlayers())).queue();
                        List<Player> playerList = ImmutableList.copyOf(JSDISCORDWEBHOOK.getServer().getOnlinePlayers());
                        if (playerList.size() > 0) {
                            StringBuilder onlinePlayers = new StringBuilder("Player(s): ");
                            for (int i = 0; i < playerList.size(); i++) {
                                if ((i == playerList.size() - 1) && JSDISCORDWEBHOOK.getServer().getOnlinePlayers().size() != 1) {
                                    onlinePlayers.append(playerList.get(i).getName()).append(", ");
                                }
                                else {
                                    onlinePlayers.append(playerList.get(i).getName());
                                }
                            }
                            event.getChannel().sendMessage(onlinePlayers.toString()).queue();
                        }
                    }
                }
            } else {
                String message = "";
                message += ChatColor.GOLD + "[Discord] ";
                message += ChatColor.RESET + event.getAuthor().getName() +" > ";
                message += event.getMessage().getContentRaw();
                JSDISCORDWEBHOOK.getServer().broadcastMessage(message);

            }
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event){
        JSDISCORDWEBHOOK.getLogger().info("Bot successfully loaded.");
    }
}
