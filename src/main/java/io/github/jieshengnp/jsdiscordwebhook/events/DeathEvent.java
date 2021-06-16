package io.github.jieshengnp.jsdiscordwebhook.events;

import io.github.jieshengnp.jsdiscordwebhook.JSDiscordWebhook;
import io.github.jieshengnp.jsdiscordwebhook.discord.WebhookSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;

public class DeathEvent implements Listener {
    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event){
        WebhookSender webhook = new WebhookSender(JSDiscordWebhook.getWebhookLink());
        webhook.setUsername(event.getEntity().getName());
        webhook.addEmbed(new WebhookSender.EmbedObject().setDescription(event.getDeathMessage()).setColor(Color.YELLOW));
        webhook.sendWebhook(event.getEntity().getServer().getLogger(), "Error sending death messages to Discord");
    }
}
