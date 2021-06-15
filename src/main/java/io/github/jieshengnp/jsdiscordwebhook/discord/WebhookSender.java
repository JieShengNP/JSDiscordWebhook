package io.github.jieshengnp.jsdiscordwebhook.discord;

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
    public boolean sendWebhook(Logger logger, String errorMessage){
        try {
            this.execute();
        } catch (java.io.IOException e){
            logger.severe(errorMessage);
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
