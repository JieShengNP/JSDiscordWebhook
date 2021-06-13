package io.github.jieshengnp.jsdiscordwebhook.EventListeners;

import io.github.jieshengnp.jsdiscordwebhook.Discord.DiscordWebhook;
import io.github.jieshengnp.jsdiscordwebhook.JSDiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        DiscordWebhook webhook = new DiscordWebhook(JSDiscordWebhook.getWebhookLink());
//        String leaveMessage = event.getPlayer().getName() + " has left the server.";
//        webhook.addEmbed(new DiscordWebhook.EmbedObject().setDescription(leaveMessage));
        webhook.setUsername("Server");
        webhook.setContent("[-] **" + event.getPlayer().getName() + "**");
        try{
            webhook.execute();
        } catch (java.io.IOException e){
            event.getPlayer().getServer().getLogger().severe(e.getStackTrace().toString());
        }
    }
}
