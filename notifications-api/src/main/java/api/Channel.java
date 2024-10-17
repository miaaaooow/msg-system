package api;

public interface Channel {
    ChannelType getChannelType();

    void sendMessage(Notification notification);
}
