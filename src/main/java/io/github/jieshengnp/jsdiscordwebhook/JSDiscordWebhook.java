package io.github.jieshengnp.jsdiscordwebhook;

import io.github.jieshengnp.jsdiscordwebhook.discord.DiscordBotListener;
import io.github.jieshengnp.jsdiscordwebhook.discord.WebhookSender;
import io.github.jieshengnp.jsdiscordwebhook.events.ChatEvent;
import io.github.jieshengnp.jsdiscordwebhook.events.JoinEvent;
import io.github.jieshengnp.jsdiscordwebhook.events.LeaveEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.awt.*;

public final class JSDiscordWebhook extends JavaPlugin {

    private final Plugin plugin = this;
    private static String webhooklink;
    private static JDA jda;
    private static boolean webhookEnabled;

    @Override
    public void onEnable() {
        plugin.saveDefaultConfig();
        WebhookStartup();
        if (webhookEnabled) {
            BotStartup();
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
        if (jda != null){
            jda.shutdownNow();
        }
        if (webhookEnabled) {
            WebhookSender webhook = new WebhookSender(webhooklink);
            webhook.setUsername("Server");
            webhook.addEmbed(new WebhookSender.EmbedObject().setDescription("Server is now closing...").setColor(Color.RED));
            webhook.sendWebhook(plugin.getLogger(), "Error sending message on shutdown");
        }
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[JSDiscordWebhook] is now Disabled.");
    }

    public static String getWebhookLink(){
        return webhooklink;
    }

    public void WebhookStartup() {
        webhooklink = plugin.getConfig().getString("webhook-url", "");
        if (webhooklink.startsWith("https://discord.com/api/webhooks/")) {
            WebhookSender webhook = new WebhookSender(webhooklink);
            webhook.setUsername("Server");
            webhook.addEmbed(new WebhookSender.EmbedObject().setDescription("Server is starting...").setColor(Color.GREEN));
            if (webhook.sendWebhook(plugin.getLogger(), "Error sending message on start-up")) {
                webhookEnabled = true;
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[JSDiscordWebhook] is now Enabled.");
                plugin.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
                plugin.getServer().getPluginManager().registerEvents(new ChatEvent(), this);
                plugin.getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
                return;
            }
        }
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Discord Webhook link might be improperly configured.");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "It should start with \"https://discord.com/api/webhooks/\"");
        this.setEnabled(false);
        plugin.getServer().getPluginManager().disablePlugin(plugin);
    }

    public void BotStartup(){
        String botToken = plugin.getConfig().getString("bot-token");
        String botPrefix = plugin.getConfig().getString("bot-prefix");
        String botChannel = plugin.getConfig().getString("bot-channel-name");
        try {
            jda = JDABuilder.createDefault(botToken).build();
        } catch (LoginException e) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Error creating Discord-Bot.");
            e.printStackTrace();
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Check your bot token?");
        }
        if (jda != null){
            jda.addEventListener(new DiscordBotListener(this, botPrefix, botChannel));
        } else {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Discord Bot is not initialised.");
        }
    }
}
