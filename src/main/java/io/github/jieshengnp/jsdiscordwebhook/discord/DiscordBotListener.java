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

    private JSDiscordWebhook jsDiscordWebhook;
    private String prefix;
    private String channelName;

    public DiscordBotListener(JSDiscordWebhook jsDiscordWebhook, String prefix, String channelName){
        this.jsDiscordWebhook = jsDiscordWebhook;
        this.prefix = prefix;
        this.channelName = channelName;
    }
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        if (!event.getAuthor().isBot()){
            if (event.getMessage().getContentRaw().startsWith(prefix)){
                String[] args = event.getMessage().getContentRaw().split("\\s+");
                if (event.getChannel().getName().equalsIgnoreCase(channelName)){
                    if (args[0].equalsIgnoreCase(prefix + "list")){
                        event.getChannel().sendMessage(String.format("**[%d/%d] players online.**", jsDiscordWebhook.getServer().getOnlinePlayers().size(), jsDiscordWebhook.getServer().getMaxPlayers())).queue();
                        List<Player> playerList = ImmutableList.copyOf(jsDiscordWebhook.getServer().getOnlinePlayers());
                        if (playerList.size() > 0) {
                            String onlinePlayers = "Player(s): ";
                            for (int i = 0; i < playerList.size(); i++) {
                                if ((i == playerList.size() - 1) && jsDiscordWebhook.getServer().getOnlinePlayers().size() != 1) {
                                    onlinePlayers += playerList.get(i).getName() + ", ";
                                }
                                else {
                                    onlinePlayers += playerList.get(i).getName();
                                }
                            }
                            event.getChannel().sendMessage(onlinePlayers).queue();
                        }
                    }
                }
            } else {
                String message = "";
                message += ChatColor.GOLD + "[Discord] ";
                message += ChatColor.RESET + event.getAuthor().getName() +" > ";
                message += event.getMessage().getContentRaw();
                jsDiscordWebhook.getServer().broadcastMessage(message);

            }
        }
    }

    @Override
    public void onReady(ReadyEvent event){
        jsDiscordWebhook.getLogger().info("Bot successfully loaded.");
    }
}
