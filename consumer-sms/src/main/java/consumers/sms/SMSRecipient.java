package consumers.sms;

import notifications.api.Recipient;

public interface SMSRecipient extends Recipient {
    String getPhoneNumber();
}
