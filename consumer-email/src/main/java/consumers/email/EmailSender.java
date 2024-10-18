package consumers.email;

import notifications.api.Notification;
import notifications.api.NotificationChannelType;
import notifications.api.recipient.EmailRecipient;
import consumer.BasicNotificationConsumer;
import consumer.NotificationConsumer;

public class EmailSender extends BasicNotificationConsumer implements NotificationConsumer {

    @Override
    public String prepareSenderIntroMessage(Notification notification) {
        EmailRecipient recipient = (EmailRecipient) notification.recipient();
        return String.format("Sending Email %s to user %s (%s)...",
                notification.title(),
                recipient.getName(),
                recipient.getEmail());
    }

    @Override
    public String prepareSenderOutroMessage(Notification notification) {
        return String.format("Email sent. %s", notification.title());
    }

    @Override
    public int pretendToWorkForMillis() {
        return 1000;
    }

    @Override
    public NotificationChannelType getChannelType() {
        return NotificationChannelType.EMAIL;
    }
}
