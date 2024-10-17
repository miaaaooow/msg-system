package consumers.email;

import notifications.api.recipient.EmailRecipient;
import notifications.api.recipient.Recipient;

public class EmailRecipientImpl implements Recipient, EmailRecipient {
    private final String id;
    private final String name;
    private final String email;

    public EmailRecipientImpl(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
