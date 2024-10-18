package notifications.api;

import notifications.api.recipient.Recipient;

import java.util.List;

public record Notification (String title,
                            String content,
                            List<Object> metadata,
                            Recipient recipient,
                            NotificationChannelType channelType) {

    public static final String SEPARATOR = ":::::";

    public Notification(String content, Recipient recipient, NotificationChannelType channelType) {
        this("MSG", content, List.of(), recipient, channelType);
    }

}

