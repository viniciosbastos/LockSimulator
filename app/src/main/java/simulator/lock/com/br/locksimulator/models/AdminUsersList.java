package simulator.lock.com.br.locksimulator.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class AdminUsersList implements Serializable{
    private final long serialVersionUID = 1L;
    private ArrayList<User> adminUsers;

    public AdminUsersList() {
        this.adminUsers = new ArrayList<>();
    }

    public AdminUsersList(ArrayList<User> adminUsers) {
        this.adminUsers = adminUsers;
    }

    public ArrayList<User> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(ArrayList<User> adminUsers) {
        this.adminUsers = adminUsers;
    }

    public void add(User user) {
        this.adminUsers.add(user);
    }

    public void remove(User user) {
        this.adminUsers.remove(user);
    }

    public void remove(int index) {
        this.adminUsers.remove(index);
    }

    public boolean isAdmin(User user) {
        boolean isAdmin = false;

        for (User u : adminUsers) {
            if (u.equals(user)) {
                isAdmin = true;
                break;
            }
        }

        return isAdmin;
    }
}
