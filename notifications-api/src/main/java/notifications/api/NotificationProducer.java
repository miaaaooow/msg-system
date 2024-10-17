package notifications.api;

import java.util.List;

public interface NotificationProducer {

    List<Notification> getProduced();
}
