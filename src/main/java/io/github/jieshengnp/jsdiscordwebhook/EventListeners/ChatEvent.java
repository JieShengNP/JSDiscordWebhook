package io.github.jieshengnp.jsdiscordwebhook.EventListeners;

import io.github.jieshengnp.jsdiscordwebhook.Discord.WebhookSender;
import io.github.jieshengnp.jsdiscordwebhook.JSDiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        WebhookSender webhook = new WebhookSender(JSDiscordWebhook.getWebhookLink());
        webhook.setUsername(event.getPlayer().getName());
        webhook.setAvatarUrl("https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        webhook.setContent(event.getMessage().replace("@everyone", "`@everyone`").replace("@here", "`@here`"));
        webhook.sendWebhook(event.getPlayer().getServer().getLogger(), "Join Sync Error");
    }
}
