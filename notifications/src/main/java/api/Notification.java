package api;

import java.util.List;

public interface Notification {
    String getTitle();
    String getContent();
    List<Object> getMetadata();
}
