package io.github.jieshengnp.jsdiscordwebhook.events;

import io.github.jieshengnp.jsdiscordwebhook.discord.WebhookSender;
import io.github.jieshengnp.jsdiscordwebhook.JSDiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        WebhookSender webhook = new WebhookSender(JSDiscordWebhook.getWebhookLink());
        webhook.setUsername("Server");
        webhook.setContent("[-] **" + event.getPlayer().getName() + "**");
        webhook.sendWebhook(event.getPlayer().getServer().getLogger(), "Error sending player quit messages to Discord");

    }
}
