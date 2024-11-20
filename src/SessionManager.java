import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;

public class SessionManager {
    //private ConcurrentHashMap<String, Session> active_sessions = new ConcurrentHashMap<>();
    private Session active_session = null;
    public long timeout_minutes = 1;

//    public void start_session(String username) {
//        active_sessions.put(username, new Session());
//    }

    public void start_session() {
        this.active_session = new Session();
    }

    public boolean check_session() {

        if (this.active_session == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(this.active_session.get_last_activity().plusMinutes(timeout_minutes))) {
            this.active_session = null;
            return false;
        }

        this.active_session.update_activity();
        return true;
    }
}