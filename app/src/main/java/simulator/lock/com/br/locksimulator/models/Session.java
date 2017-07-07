package simulator.lock.com.br.locksimulator.models;

/**
 * Created by Vinicios on 04/07/2017.
 */

public class Session {
    private User activeUser;

    private boolean admin;

    private static Session instance;

    private Session() {
        this.admin = false;
    }

    public static Session getInstance() {
        if (instance == null)
            instance = new Session();
        return instance;
    }

    public void setActiveUser(User user) {
        this.activeUser = user;
    }

    public User getActiveUser() {
        return this.activeUser;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
