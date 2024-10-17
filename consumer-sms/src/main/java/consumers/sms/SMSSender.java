package consumers.sms;

import api.ChannelType;
import api.Notification;
import api.NotificationConsumer;

public class SMSSender implements NotificationConsumer {
    @Override
    public void sendNotification(Notification notification, ChannelType channelType) {
        System.out.println("Sending SMS... " + notification.getTitle());
    }
}
