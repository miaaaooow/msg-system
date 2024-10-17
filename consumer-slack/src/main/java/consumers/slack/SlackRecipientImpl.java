package consumers.slack;

public class SlackRecipientImpl implements notifications.api.recipient.SlackRecipient {
    private final String id;
    private final String name;
    private final String slackChannel;

    public SlackRecipientImpl(String id, String name, String slackChannel) {
        this.id = id;
        this.name = name;
        this.slackChannel = slackChannel;
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSlackChannel() {
        return slackChannel;
    }
}
