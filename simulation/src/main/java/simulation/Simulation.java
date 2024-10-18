package simulation;

import consumer.NotificationConsumer;
import consumers.email.EmailSender;
import consumers.slack.SlackSender;
import consumers.sms.SMSSender;
import notifications.api.Notification;
import notifications.api.recipient.Recipient;
import producer.NotificationQueuePublisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public static void main(String[] args) throws IOException {
        NotificationQueuePublisher publisher = new NotificationQueuePublisher();


        for (int i = 0; i < 100; i++) {
            Notification notification = NotificationGenerator.generateNotification();
            Recipient recipient = notification.recipient();
            System.out.println("Notification channel:" + notification.channelType().name() + " MSG title: " + notification.title() + " MSG content " + notification.content());
            System.out.println("  sent to " + recipient.getName());

            publisher.publishNotification(notification);

        }

        List<NotificationConsumer> consumers = new ArrayList<>(30);
        for (int i = 0; i < 10; i++) {
            EmailSender emailSenderA = new EmailSender();
            SMSSender smsSenderA = new SMSSender();
            SlackSender slackSenderA = new SlackSender();
            consumers.add(emailSenderA);
            consumers.add(smsSenderA);
            consumers.add(slackSenderA);
        }

        for (NotificationConsumer consumer: consumers) {
            Thread thread = new Thread(new ConsumerRunnable(consumer));
            thread.start();
        }

    }
}
