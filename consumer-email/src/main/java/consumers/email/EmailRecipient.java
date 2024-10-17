package consumers.email;

import api.Recipient;

public interface EmailRecipient extends Recipient {
    String getEmail();
}
