package io.github.jieshengnp.jsdiscordwebhook.EventListeners;

import io.github.jieshengnp.jsdiscordwebhook.Discord.DiscordWebhook;
import io.github.jieshengnp.jsdiscordwebhook.JSDiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        DiscordWebhook webhook = new DiscordWebhook(JSDiscordWebhook.getWebhookLink());
        webhook.setUsername(event.getPlayer().getName());
        webhook.setAvatarUrl("https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        webhook.setContent(event.getMessage());
        try{
            webhook.execute();
        } catch (java.io.IOException e){
            event.getPlayer().getServer().getLogger().severe(e.getStackTrace().toString());
        }
    }
}
