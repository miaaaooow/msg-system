package notifications.api.recipient;

public interface SMSRecipient extends Recipient {
    String getPhoneNumber();
}
