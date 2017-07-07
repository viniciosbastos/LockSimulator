package simulator.lock.com.br.locksimulator.handlers;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import simulator.lock.com.br.locksimulator.MainActivity;
import simulator.lock.com.br.locksimulator.models.PermitedUsersList;
import simulator.lock.com.br.locksimulator.models.PermitedUsersStorage;
import simulator.lock.com.br.locksimulator.models.Session;
import simulator.lock.com.br.locksimulator.models.User;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class GrantPermissionHandler implements IHandler{
    private ArrayList<User> users;
    private Handler handler;

    private byte[] command;
    private byte[] response;

    private byte result;

    public GrantPermissionHandler(byte[] command, Handler handler) {
        this.users = new ArrayList<>();
        this.command = command;
        this.handler = handler;
        decode();
    }

    @Override
    public byte[] getResponse() {
        response = new byte[4];
        response[0] = command[0];
        response[1] = command[1];
        response[2] = 1;
        response[3] = this.result;

        return this.response;
    }

    @Override
    public void handle() {
        if (Session.getInstance().isAdmin()) {
            PermitedUsersStorage storage = PermitedUsersStorage.getInstance();
            StringBuilder sb = new StringBuilder();

            for (User user : users) {
                storage.add(user);
                sb.append(user.getUsername() + ",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));


            Message msg = handler.obtainMessage();
            msg.what = MainActivity.ACCESS_GRANTED;
            msg.obj = "Admin " + Session.getInstance().getActiveUser().getUsername() + " permitiu acesso a " + sb.toString();
            msg.sendToTarget();

            this.result = 0x01;
            createResponse();
        } else {
            this.result = 0x00;
        }
    }

    private void decode() {
        byte[] username = new byte[8];
        byte[] passwd = new byte[8];
        User user;
        for (int i = 0; i < command[2]/16; i++) {
            System.arraycopy(command, 3 + (16*i), username, 0, 8);
            System.arraycopy(command, 11 + (16*i), passwd, 0, 8);
            users.add(new User(new String(username), new String(passwd)));
        }
    }

    private void createResponse() {

    }
}
