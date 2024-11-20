import java.time.LocalDateTime;
public class Session {
    //private String username;
    private LocalDateTime last_activity;

    public Session() {
        //this.username = username;
        this.last_activity = LocalDateTime.now();
    }

//    public String get_username() {
//        return this.username;
//    }

    public LocalDateTime get_last_activity() {
        return this.last_activity;
    }

    public void update_activity() {
        this.last_activity = LocalDateTime.now();
    }
}
