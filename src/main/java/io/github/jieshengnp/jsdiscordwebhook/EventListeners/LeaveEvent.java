package io.github.jieshengnp.jsdiscordwebhook.EventListeners;

import io.github.jieshengnp.jsdiscordwebhook.Discord.WebhookSender;
import io.github.jieshengnp.jsdiscordwebhook.JSDiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        WebhookSender webhook = new WebhookSender(JSDiscordWebhook.getWebhookLink());
//        String leaveMessage = event.getPlayer().getName() + " has left the server.";
//        webhook.addEmbed(new DiscordWebhook.EmbedObject().setDescription(leaveMessage));
        webhook.setUsername("Server");
        webhook.setContent("[-] **" + event.getPlayer().getName() + "**");
        webhook.sendWebhook(event.getPlayer().getServer().getLogger(), "Quit Sync Error");

    }
}
