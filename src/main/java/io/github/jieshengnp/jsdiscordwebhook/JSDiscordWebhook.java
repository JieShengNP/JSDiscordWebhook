package io.github.jieshengnp.jsdiscordwebhook;

import io.github.jieshengnp.jsdiscordwebhook.Discord.DiscordWebhook;
import io.github.jieshengnp.jsdiscordwebhook.EventListeners.ChatEvent;
import io.github.jieshengnp.jsdiscordwebhook.EventListeners.JoinEvent;
import io.github.jieshengnp.jsdiscordwebhook.EventListeners.LeaveEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public final class JSDiscordWebhook extends JavaPlugin {

    private final Plugin plugin = this;
    private static String webhooklink;
    @Override
    public void onEnable() {
        plugin.saveDefaultConfig();
        webhooklink = plugin.getConfig().getString("webhook-url");
        if (webhooklink.startsWith("https://discord.com/api/webhooks/")){
            DiscordWebhook webhook = new DiscordWebhook(webhooklink);
            webhook.setUsername("Server");
            webhook.addEmbed(new DiscordWebhook.EmbedObject().setDescription("Server is now starting...").setColor(Color.GREEN));
            try {
                webhook.execute();
            } catch (java.io.IOException e) {
                plugin.getServer().getLogger().severe(e.getStackTrace().toString());
            }
        } else {
            plugin.getServer().getLogger().severe(ChatColor.RED + "Discord Webhook link is improperly configured.");
            plugin.getServer().getLogger().severe(ChatColor.RED + "It should start with \"https://discord.com/api/webhooks/\"");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[JSDiscordWebhook] is now Enabled.");
        plugin.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        plugin.getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        plugin.getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
    }

    @Override
    public void onDisable() {
        DiscordWebhook webhook = new DiscordWebhook(webhooklink);
        webhook.setUsername("Server");
        webhook.addEmbed(new DiscordWebhook.EmbedObject().setDescription("Server is now closing...").setColor(Color.RED));
        try {
            webhook.execute();
        } catch (java.io.IOException e){
            plugin.getServer().getLogger().severe(e.getStackTrace().toString());
        }
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[JSDiscordWebhook] is now Disabled.");

    }

    public static String getWebhookLink(){
        return webhooklink;
    }
}
