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
    private static String botToken;
    private static JDA jda;
    private static String botPrefix;
    private static String botChannel;

    @Override
    public void onEnable() {
        plugin.saveDefaultConfig();
        webhooklink = plugin.getConfig().getString("webhook-url");
        botToken = plugin.getConfig().getString("bot-token");
        botPrefix = plugin.getConfig().getString("bot-prefix");
        botChannel = plugin.getConfig().getString("bot-channel-name");
        WebhookStartup();
        BotStartup();
    }

    @Override
    public void onDisable() {
        if (jda != null){
            jda.shutdownNow();
        }
        WebhookSender webhook = new WebhookSender(webhooklink);
        webhook.setUsername("Server");
        webhook.addEmbed(new WebhookSender.EmbedObject().setDescription("Server is now closing...").setColor(Color.RED));
        webhook.sendWebhook(plugin.getLogger(), "Disable Error");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[JSDiscordWebhook] is now Disabled.");
        saveConfig();
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

    public void BotStartup(){
        try {
            jda = JDABuilder.createDefault(botToken).build();
        } catch (LoginException e) {
            plugin.getLogger().severe("Error creating Bot.");
            plugin.getLogger().severe(e.getStackTrace().toString());
            plugin.getLogger().severe("Check your bot token?");
        }
        if (jda != null){
            jda.addEventListener(new DiscordBotListener(this, botPrefix, botChannel));
        } else {
            plugin.getLogger().severe("Bot is not initialised.");
        }
    }
}
