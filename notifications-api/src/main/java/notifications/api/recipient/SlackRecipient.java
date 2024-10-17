package notifications.api.recipient;

public interface SlackRecipient extends Recipient {
    String getSlackChannel();
}
