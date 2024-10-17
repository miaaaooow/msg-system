package consumers.slack;

import notifications.api.Notification;
import notifications.api.NotificationConsumer;

public class SlackSender implements NotificationConsumer {
    @Override
    public void sendNotification(Notification notification) {
        System.out.println("Sending Slack... " + notification.title());
    }
}
