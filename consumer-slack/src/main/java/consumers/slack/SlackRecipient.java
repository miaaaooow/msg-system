package consumers.slack;

import notifications.api.Recipient;

public interface SlackRecipient extends Recipient {
    String getSlackChannel();
}
