package consumers.slack;

import api.ChannelType;
import api.Notification;
import api.NotificationConsumer;

public class SlackSender implements NotificationConsumer {
    @Override
    public void sendNotification(Notification notification, ChannelType channelType) {
        System.out.println("Sending Slack... " + notification.getTitle());
    }
}
