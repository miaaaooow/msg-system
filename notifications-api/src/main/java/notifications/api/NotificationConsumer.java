package notifications.api;

public interface NotificationConsumer {
    void sendNotification(Notification notification);
}
