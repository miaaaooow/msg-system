package consumers.slack;

import api.Recipient;

public interface SlackRecipient extends Recipient {
    String getSlackChannel();
}
