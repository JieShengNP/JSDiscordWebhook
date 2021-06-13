package io.github.jieshengnp.jsdiscordwebhook.EventListeners;

import io.github.jieshengnp.jsdiscordwebhook.Discord.DiscordWebhook;
import io.github.jieshengnp.jsdiscordwebhook.JSDiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        DiscordWebhook webhook = new DiscordWebhook(JSDiscordWebhook.getWebhookLink());
//        String joinMessage = event.getPlayer().getName() + "has joined the server.";
//        String playerCount = String.format("[%d/%d] players online.", event.getPlayer().getServer().getOnlinePlayers().size(), event.getPlayer().getServer().getMaxPlayers());
//        webhook.addEmbed(new DiscordWebhook.EmbedObject().setDescription(joinMessage + "\n" + playerCount));
        webhook.setUsername("Server");
        webhook.setContent("[+] **" + event.getPlayer().getName() + "**");
        try{
            webhook.execute();
        } catch (java.io.IOException e){
            event.getPlayer().getServer().getLogger().severe(e.getStackTrace().toString());
        }
    }
}
