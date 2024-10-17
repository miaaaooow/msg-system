package producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import notifications.api.*;
import notifications.api.recipient.EmailRecipient;
import notifications.api.recipient.Recipient;
import notifications.api.recipient.SMSRecipient;
import notifications.api.recipient.SlackRecipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NotificationQueuePublisher implements NotificationProducer {
    private static final Logger logger = LoggerFactory.getLogger(NotificationQueuePublisher.class);

    private static final ConnectionFactory factory = new ConnectionFactory();
    private static Channel notificationsChannel;
    private static Connection connection;

    private static ConnectionFactory prepareConnection() {
        // "guest"/"guest" by default, limited to localhost connections
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
        return factory;
    }

    private static void prepareChannelWithQueues() {
        if (notificationsChannel == null) {
            prepareConnection();
            try (Connection conn = factory.newConnection();
                 Channel newChannel = conn.createChannel()) {
                notificationsChannel = newChannel;
                notificationsChannel.basicQos(1); // prefetch one, so that distribution is comparatively equal

                for (NotificationChannelType channelType: NotificationChannelType.values()) {
                    String queueName = channelType.name();
                    newChannel.queueDeclare(queueName, true, false, false, null);
                }
            } catch (IOException | TimeoutException e) {
                logger.error("{} {}", e.getMessage(), e.getCause());
            }
        }
    }

    public synchronized void publishNotification(Notification notification) {
        String message = formMessage(notification);
        String queueName = notification.channelType().name();
        prepareChannelWithQueues();
        try {
            notificationsChannel.basicPublish("exchange", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        } catch (IOException e) {
            logger.error("{} {}", e.getMessage(), e.getCause());
        }
        logger.info("Sent '{}' to channel {}", message, queueName);
    }

    public void closeConnections() {
        try {
            notificationsChannel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

    private String formMessage(Notification notification) {
        Recipient recipient = notification.recipient();
        StringBuilder sb = new StringBuilder(notification.title());
        sb.append(SEPARATOR);
        sb.append(notification.content());
        sb.append(SEPARATOR);
        sb.append(recipient.getName());
        sb.append(SEPARATOR);
        sb.append(recipient.getId());
        sb.append(SEPARATOR);
        switch (notification.channelType()) {
            case NotificationChannelType.EMAIL -> sb.append(((EmailRecipient) recipient).getEmail());
            case NotificationChannelType.SMS -> sb.append(((SMSRecipient) recipient).getPhoneNumber());
            case NotificationChannelType.SLACK -> sb.append(((SlackRecipient) recipient).getSlackChannel());
        }
        return sb.toString();
    }
}
