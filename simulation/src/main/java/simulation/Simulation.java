package simulation;

import consumers.email.EmailSender;
import consumers.slack.SlackSender;
import consumers.sms.SMSSender;
import notifications.api.Notification;
import notifications.api.recipient.Recipient;
import producer.NotificationQueuePublisher;

import java.io.IOException;

public class Simulation {

    public static void main(String[] args) throws IOException {
        NotificationQueuePublisher publisher = new NotificationQueuePublisher();
        EmailSender emailSender = new EmailSender();
        SMSSender smsSender = new SMSSender();
        SlackSender slackSender = new SlackSender();

        for (int i = 0; i < 100; i++) {
            Notification notification = NotificationGenerator.generateNotification();
            Recipient recipient = notification.recipient();
            System.out.println("Notification channel:" + notification.channelType().name() + " MSG title: " + notification.title() + " MSG content " + notification.content());
            System.out.println("  sent to " + recipient.getName());

            publisher.publishNotification(notification);

        }

        emailSender.consumeNotification();
        smsSender.consumeNotification();
        slackSender.consumeNotification();
    }
}
