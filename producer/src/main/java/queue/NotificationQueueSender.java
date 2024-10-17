package queue;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NotificationQueueSender {
    private static Logger logger = LoggerFactory.getLogger(NotificationQueueSender.class);

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
// "guest"/"guest" by default, limited to localhost connections
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
        try (Connection conn = factory.newConnection()) {

            System.out.println("Success!");

        } catch (IOException | TimeoutException e) {
            logger.error("ERROR " + e.getMessage());
        }
    }
}
