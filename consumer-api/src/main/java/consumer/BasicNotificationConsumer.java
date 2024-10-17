package consumer;

import notifications.api.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasicNotificationConsumer implements NotificationConsumer {
    private static final Logger logger = LoggerFactory.getLogger(BasicNotificationConsumer.class);

    @Override
    public void consumeNotification() {

    }

    @Override
    public synchronized void sendNotification(Notification notification) {
        logger.info(prepareSenderIntroMessage(notification));
        try {
            Thread.sleep(pretendToWorkForMillis());
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e.getCause());
        }
        logger.info(prepareSenderOutroMessage(notification));
    }

    public abstract int pretendToWorkForMillis();
}
