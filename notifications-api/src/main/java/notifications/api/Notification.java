package notifications.api;

import java.util.List;

public record Notification (String title,
                            String content,
                            List<Object> metadata,
                            Recipient recipient,
                            ChannelType channelType) {};

