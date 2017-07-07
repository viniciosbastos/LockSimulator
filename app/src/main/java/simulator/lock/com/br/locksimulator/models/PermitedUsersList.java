package simulator.lock.com.br.locksimulator.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class PermitedUsersList implements Serializable{
    private final long serialVersionUID = 1L;
    private ArrayList<User> permitedUsers;

    public PermitedUsersList() {
        this.permitedUsers = new ArrayList<>();
    }

    public PermitedUsersList(ArrayList<User> permitedUsers) {
        this.permitedUsers = permitedUsers;
    }

    public ArrayList<User> getPermitedUsers() {
        return permitedUsers;
    }

    public void setPermitedUsers(ArrayList<User> permitedUsers) {
        this.permitedUsers = permitedUsers;
    }

    public void add(User user) {
        this.permitedUsers.add(user);
    }

    public void remove(User user) {
        this.permitedUsers.remove(user);
    }

    public void remove(int index) {
        this.permitedUsers.remove(index);
    }

    public boolean isPermited(User user) {
        boolean isPermited = false;

        for (User u : permitedUsers) {
            if (u.equals(user)) {
                isPermited = true;
                break;
            }
        }

        return isPermited;
    }}
