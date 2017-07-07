package simulator.lock.com.br.locksimulator.handlers;

import android.os.Handler;
import android.os.Message;

import simulator.lock.com.br.locksimulator.MainActivity;
import simulator.lock.com.br.locksimulator.models.AdminStorage;
import simulator.lock.com.br.locksimulator.models.Session;
import simulator.lock.com.br.locksimulator.models.User;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class LoginHandler implements IHandler{
    private User user;
    private byte[] command;
    private byte[] response;
    private boolean isAdmin;
    private Handler handler;

    public LoginHandler(byte[] command, Handler handler) {
        this.command = command;
        this.isAdmin = false;
        this.handler = handler;
        decode();
    }

    @Override
    public byte[] getResponse() {
        return this.response;
    }

    private void decode() {
        byte[] username = new byte[8];
        byte[] passwd = new byte[8];

        System.arraycopy(command, 3, username, 0, 8);
        System.arraycopy(command, 11, passwd, 0, 8);
        user = new User(new String(username), new String(passwd));
    }

    @Override
    public void handle() {
        testIsAdmin();
        createResponse();
    }

    private void createResponse() {
        response = new byte[4];
        response[0] = command[0];
        response[1] = command[1];
        response[2] = 1;

        if (isAdmin) {
            response[3] = (byte) 0x01;
        } else {
            response[3] = (byte) 0x00;
        }
    }

    private void testIsAdmin() {
        for (User user : AdminStorage.getInstance().getAdminList()) {
            if (this.user.equals(user)) {
                this.isAdmin = true;
                break;
            }
        }

        if (this.isAdmin) {
            Session.getInstance().setActiveUser(this.user);
            Session.getInstance().setAdmin(true);
            Message msg = handler.obtainMessage();
            msg.what = MainActivity.ADMIN_LOGGED;
            msg.obj = "Admin " + this.user.getUsername() + " logou";
            msg.sendToTarget();
        }
    }
}
