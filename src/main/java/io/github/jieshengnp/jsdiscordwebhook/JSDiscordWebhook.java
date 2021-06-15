package io.github.jieshengnp.jsdiscordwebhook;

import io.github.jieshengnp.jsdiscordwebhook.Discord.WebhookSender;
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
        WebhookStartup();
    }

    @Override
    public void onDisable() {
        WebhookSender webhook = new WebhookSender(webhooklink);
        webhook.setUsername("Server");
        webhook.addEmbed(new WebhookSender.EmbedObject().setDescription("Server is now closing...").setColor(Color.RED));
        webhook.sendWebhook(plugin.getLogger(), "Disable Error");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[JSDiscordWebhook] is now Disabled.");

    }

    public static String getWebhookLink(){
        return webhooklink;
    }

    public void WebhookStartup(){
        if (webhooklink.startsWith("https://discord.com/api/webhooks/")){
            WebhookSender webhook = new WebhookSender(webhooklink);
            webhook.setUsername("Server");
            webhook.addEmbed(new WebhookSender.EmbedObject().setDescription("Server is now starting...").setColor(Color.GREEN));
            webhook.sendWebhook(plugin.getLogger(), "Startup Error");
        } else {
            plugin.getLogger().severe(ChatColor.RED + "Discord Webhook link is improperly configured.");
            plugin.getLogger().severe(ChatColor.RED + "It should start with \"https://discord.com/api/webhooks/\"");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[JSDiscordWebhook] is now Enabled.");
        plugin.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        plugin.getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        plugin.getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
    }
}
