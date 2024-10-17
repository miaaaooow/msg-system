package api;

public interface NotificationConsumer {
    void sendNotification(Notification notification, ChannelType channelType);
}
