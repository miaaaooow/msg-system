package notifications.api;

public interface Channel {
    ChannelType getChannelType();

    void sendMessage(Notification notification);
}
