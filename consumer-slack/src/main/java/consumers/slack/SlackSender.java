package consumers.slack;

import notifications.api.Notification;
import consumer.BasicNotificationConsumer;
import consumer.NotificationConsumer;
import notifications.api.NotificationChannelType;
import notifications.api.recipient.SlackRecipient;

public class SlackSender extends BasicNotificationConsumer implements NotificationConsumer {

    @Override
    public String prepareSenderIntroMessage(Notification notification) {
        SlackRecipient slackRecipient = (SlackRecipient) notification.recipient();
        String receiver = slackRecipient.getSlackChannel() != null
                ? slackRecipient.getSlackChannel()
                : slackRecipient.getName();
        return String.format("Sending Slack %s to user %s or channel %s...", notification.title(),
                slackRecipient.getName(),
                receiver);
    }

    @Override
    public String prepareSenderOutroMessage(Notification notification) {
        return String.format("Slack sent. %s", notification.title());
    }

    @Override
    public int pretendToWorkForMillis() {
        return 200;
    }

    @Override
    public NotificationChannelType getChannelType() {
        return NotificationChannelType.SLACK;
    }
}
