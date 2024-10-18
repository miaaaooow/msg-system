package consumer;

import notifications.api.Notification;
import notifications.api.NotificationChannelType;
import notifications.api.recipient.EmailRecipient;
import notifications.api.recipient.Recipient;
import notifications.api.recipient.SMSRecipient;
import notifications.api.recipient.SlackRecipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class NotificationRestorer {
    private static final Logger logger = LoggerFactory.getLogger(NotificationRestorer.class);

    static Notification restoreNotification(String message, NotificationChannelType channelType) {
        String[] msgParts = message.split(Notification.SEPARATOR);
        if (msgParts.length != 5) {
            logger.error("Potentially corrupted message: {}", message);
            return null;
        }

        Recipient recipient = getRecipient(channelType, msgParts);

        return new Notification(msgParts[0], msgParts[1], List.of(), recipient, channelType);
    }

    private static Recipient getRecipient(NotificationChannelType channelType, String[] msgParts) {
        Recipient recipient = null;
        switch (channelType) {
            case NotificationChannelType.EMAIL -> recipient = new EmailRecipient() {
                @Override
                public String getId() {
                    return msgParts[3];
                }

                @Override
                public String getName() {
                    return msgParts[2];
                }

                @Override
                public String getEmail() {
                    return msgParts[4];
                }
            };
            case NotificationChannelType.SLACK -> recipient = new SlackRecipient() {
                @Override
                public String getId() {
                    return msgParts[3];
                }

                @Override
                public String getName() {
                    return msgParts[2];
                }

                @Override
                public String getSlackChannel() {
                    return msgParts[4];
                }
            };
            case NotificationChannelType.SMS -> recipient = new SMSRecipient() {
                @Override
                public String getId() {
                    return msgParts[3];
                }

                @Override
                public String getName() {
                    return msgParts[2];
                }

                @Override
                public String getPhoneNumber() {
                    return msgParts[4];
                }
            };
        }
        return recipient;
    }
}
