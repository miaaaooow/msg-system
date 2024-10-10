package api;

import java.util.List;

public interface NotificationAcceptor {
    void acceptMessage(Notification notification, ChannelType channelType);
}
