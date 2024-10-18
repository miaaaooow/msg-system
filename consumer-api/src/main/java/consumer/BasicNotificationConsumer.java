package consumer;

import com.rabbitmq.client.*;
import notifications.api.Notification;
import notifications.api.NotificationChannelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class BasicNotificationConsumer implements NotificationConsumer {
    private static final Logger logger = LoggerFactory.getLogger(BasicNotificationConsumer.class);

    private static ConnectionFactory factory;

    private static void prepareConnection() {
        // "guest"/"guest" by default, limited to localhost connections
        factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
    }

    private static void prepareChannelWithQueues() {
        if (factory == null) {
            prepareConnection();
            try (Connection conn = factory.newConnection();
                 Channel newChannel = conn.createChannel()) {

                newChannel.basicQos(1); // prefetch one, so that distribution is comparatively equal

                for (NotificationChannelType channelType : NotificationChannelType.values()) {
                    String queueName = channelType.name();
                    newChannel.queueDeclare(queueName, true, false, false, null);
                }
            } catch (IOException | TimeoutException e) {
                logger.error("{} {}", e.getMessage(), e.getCause());
            }
        }
    }


    @Override
    public synchronized void consumeNotification() throws IOException {
        prepareChannelWithQueues();

        try (final Connection connection = factory.newConnection();
             final Channel channel = connection.createChannel()) {

            channel.basicQos(0, 1, false);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                Notification notification = NotificationRestorer.restoreNotification(message, getChannelType());

                logger.info("Consumed " + notification.channelType() + " '" + notification.title() + "' for user " + notification.recipient().getName());
                try {
                    sendNotification(notification);
                } finally {
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(getChannelType().name(), false, deliverCallback, consumerTag -> {
            });

        } catch (TimeoutException e) {
            logger.error(e.getMessage());
        }

    }

    protected abstract NotificationChannelType getChannelType();

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

    protected abstract int pretendToWorkForMillis();
}
