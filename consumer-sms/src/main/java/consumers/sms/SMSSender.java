package consumers.sms;

import notifications.api.Notification;
import consumer.BasicNotificationConsumer;
import consumer.NotificationConsumer;
import notifications.api.recipient.SMSRecipient;

public class SMSSender extends BasicNotificationConsumer implements NotificationConsumer {

    @Override
    public String prepareSenderIntroMessage(Notification notification) {
        SMSRecipient recipient = (SMSRecipient) notification.recipient();
        return String.format("Sending SMS %s to user %s (%s)...",
                notification.title(),
                recipient.getName(),
                recipient.getPhoneNumber());
    }

    @Override
    public String prepareSenderOutroMessage(Notification notification) {
        return String.format("SMS sent. %s", notification.title());
    }

    @Override
    public int pretendToWorkForMillis() {
        return 500;
    }
}
