package consumers.email;

import notifications.api.Recipient;

public interface EmailRecipient extends Recipient {
    String getEmail();
}
