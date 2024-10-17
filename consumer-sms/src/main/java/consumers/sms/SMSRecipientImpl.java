package consumers.sms;

import notifications.api.recipient.SMSRecipient;

public class SMSRecipientImpl implements SMSRecipient {

    private final String id;
    private final String name;
    private final String phoneNumber;

    public SMSRecipientImpl(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
