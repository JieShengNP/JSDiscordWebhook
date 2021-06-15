package io.github.jieshengnp.jsdiscordwebhook.Discord;

import java.util.logging.Logger;

public class WebhookSender extends DiscordWebhook {
    public WebhookSender(String url) {
        super(url);
    }
    /**
     * Attempts to send a webhook request.
     * @param logger The output source.
     * @param errorMessage The error message if exception occurs. (With stacktrace)
     **/
    public void sendWebhook(Logger logger, String errorMessage){
        try {
            this.execute();
        } catch (java.io.IOException e){
            logger.severe(errorMessage);
            logger.severe(e.getStackTrace().toString());
        }
    }
}
