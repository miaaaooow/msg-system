package simulation;

import notifications.api.Notification;
import notifications.api.NotificationChannelType;
import notifications.api.recipient.Recipient;

import java.util.List;
import java.util.Random;

final class NotificationGenerator {
    private static final Random random = new Random();

    private static final List<String> randomTopics = List.of("Cats", "Dogs", "Birds", "Owls in specific",
            "Owls are not what they seem", "Twin Peaks Season Three", "The Truth is Out There", "Bananas");

    private static Notification generateNotification(NotificationChannelType channelType) {
        Recipient recipient = RecipientGenerator.generateRecipient(channelType);
        String title = randomTopics.get(random.nextInt(randomTopics.size()));
        String content = "Dear " + recipient.getName() + ", the season of " + title + " is coming...";
        return new Notification(title, content, List.of(), recipient, channelType);
    }

    static Notification generateNotification() {
        int channel = random.nextInt(NotificationChannelType.values().length);
        return generateNotification(NotificationChannelType.values()[channel]);
    }

}
