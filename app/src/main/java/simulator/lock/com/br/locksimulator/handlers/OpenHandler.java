package simulator.lock.com.br.locksimulator.handlers;

import android.os.Handler;
import android.os.Message;

import simulator.lock.com.br.locksimulator.MainActivity;
import simulator.lock.com.br.locksimulator.models.AdminStorage;
import simulator.lock.com.br.locksimulator.models.PermitedUsersStorage;
import simulator.lock.com.br.locksimulator.models.Session;
import simulator.lock.com.br.locksimulator.models.User;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class OpenHandler implements IHandler {
    private User user;
    private byte[] command;
    private byte[] response;
    private boolean isPermited;
    private Handler handler;

    public OpenHandler(byte[] command, Handler handler) {
        this.command = command;
        this.isPermited = false;
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
        test();
        createResponse();
    }

    private void createResponse() {
        response = new byte[4];
        response[0] = command[0];
        response[1] = command[1];
        response[2] = 1;

        if (isPermited) {
            response[3] = (byte) 0x01;
        } else {
            response[3] = (byte) 0x00;
        }
    }
    private void testPermited() {
        for (User user : PermitedUsersStorage.getInstance().getPermitedList()) {
            if (this.user.equals(user)) {
                this.isPermited = true;
                break;
            }
        }
    }

    private void testAdmin() {
        for (User user : AdminStorage.getInstance().getAdminList()) {
            if (this.user.equals(user)) {
                this.isPermited = true;
                break;
            }
        }
    }

    private void test() {
        testPermited();
        testAdmin();

        if (this.isPermited) {
            Session.getInstance().setActiveUser(this.user);
            Message msg = handler.obtainMessage();
            msg.what = MainActivity.OPEN_LOCK;
            msg.obj = this.user.getUsername() + " abriu a porta";
            msg.sendToTarget();
        }
    }
}
