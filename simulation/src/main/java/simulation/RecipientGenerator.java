package simulation;

import consumers.email.EmailRecipientImpl;
import consumers.slack.SlackRecipientImpl;
import consumers.sms.SMSRecipientImpl;
import notifications.api.NotificationChannelType;
import notifications.api.recipient.Recipient;

import java.util.List;
import java.util.Random;
import java.util.UUID;

final class RecipientGenerator {
    private static final Random random = new Random();

    private static final List<String> randomNames = List.of("Sheldon Cooper", "Amy Farrah Fowler", "Raj Koothrapalli",
            "Leonard Hofstadter", "Bernadette Rostenkowski", "Penny Hofstadter", "Gregory House", "Lisa Cuddy",
            "Dana Scully", "Fox Mulder", "Jammie Lannister", "Therion Lannister", "Brienne of Tarth", "Bran", "Bron");


    static Recipient generateRecipient(NotificationChannelType channelType) {
        String name = randomNames.get(random.nextInt(randomNames.size()));
        String id = UUID.randomUUID().toString();
        switch (channelType) {
            case NotificationChannelType.EMAIL -> {
                return new EmailRecipientImpl(id, name, makeEmail(name));
            }
            case NotificationChannelType.SLACK -> {
                return new SlackRecipientImpl(id, name, "#tv_channel");
            }
            case  NotificationChannelType.SMS -> {
                return new SMSRecipientImpl(id, name, makePhoneNumber());
            }
        }
        return null;
    }

    private static String makeEmail(String name) {
        return name.replace(" ", "_").toLowerCase() + "@coolness.com";
    }

    private static String makePhoneNumber() {
        return "0884" + random.nextInt(100000000);
    }
}
