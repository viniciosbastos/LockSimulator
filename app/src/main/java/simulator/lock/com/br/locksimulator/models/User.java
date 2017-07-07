package simulator.lock.com.br.locksimulator.models;

import java.io.Serializable;

/**
 * Created by Vinicios on 04/07/2017.
 */

public class User implements Serializable{
    private final long serialVersionUID = 1L;
    private String username;
    private String passwd;

    public User(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public boolean equals(User other) {
        if (this.username.equals(other.getUsername()) && this.passwd.equals(other.getPasswd()))
            return true;
        return false;
    }
}
