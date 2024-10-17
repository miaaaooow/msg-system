package consumers.sms;

import api.Recipient;

public interface SMSRecipient extends Recipient {
    String getPhoneNumber();
}
