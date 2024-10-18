package producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import notifications.api.Notification;
import notifications.api.NotificationChannelType;
import notifications.api.recipient.EmailRecipient;
import notifications.api.recipient.Recipient;
import notifications.api.recipient.SMSRecipient;
import notifications.api.recipient.SlackRecipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static notifications.api.Notification.SEPARATOR;

public class NotificationQueuePublisher implements NotificationProducer {
    private static final Logger logger = LoggerFactory.getLogger(NotificationQueuePublisher.class);

    private static ConnectionFactory factory;


    private static ConnectionFactory prepareConnectionFactory() {
        // "guest"/"guest" by default, limited to localhost connections
        factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
        return factory;
    }

    private static void prepareChannelWithQueues() {
        if (factory == null) {
            prepareConnectionFactory();
            try (Connection conn = factory.newConnection();
                 Channel newChannel = conn.createChannel()) {

                newChannel.basicQos(1); // prefetch one, so that distribution is comparatively equal

                for (NotificationChannelType channelType : NotificationChannelType.values()) {
                    String queueName = channelType.name();
                    newChannel.queueDeclare(queueName, false, false, false, null);
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
        try (Connection connection = factory.newConnection();
             Channel newChannel = connection.createChannel()) {
            newChannel.basicQos(0, 1, false);
            newChannel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        } catch (IOException | TimeoutException e) {
            logger.error("{} {}", e.getMessage(), e.getCause());
        }
        logger.info("Sent '{}' to channel {}", message, queueName);
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
