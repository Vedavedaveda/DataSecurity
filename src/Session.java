import java.time.LocalDateTime;
public class Session {
    private LocalDateTime last_activity;

    public Session() {
        this.last_activity = LocalDateTime.now();
    }

    public LocalDateTime get_last_activity() {
        return this.last_activity;
    }

    public void update_activity() {
        this.last_activity = LocalDateTime.now();
    }
}
