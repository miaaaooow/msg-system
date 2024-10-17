package consumers.email;

import api.ChannelType;
import api.Notification;
import api.NotificationConsumer;

public class EmailSender implements NotificationConsumer {
    @Override
    public void sendNotification(Notification notification, ChannelType channelType) {
        System.out.println("Sending Email... " + notification.getTitle());
    }
}
