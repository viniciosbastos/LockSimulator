package simulator.lock.com.br.locksimulator.models;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import simulator.lock.com.br.locksimulator.MainActivity;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class PermitedUsersStorage {

    private String FILE_NAME = "permited_storage.ps";

    private PermitedUsersList permitedList;

    private static PermitedUsersStorage instance;

    private PermitedUsersStorage() {
        readFile();
    }

    public static PermitedUsersStorage getInstance() {
        if (instance == null)
            instance = new PermitedUsersStorage();
        return instance;
    }

    private void updateFile() {
        try {
            FileOutputStream fos = MainActivity.getContext().openFileOutput(this.FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.permitedList);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        try {
            FileInputStream fis = MainActivity.getContext().openFileInput(this.FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.permitedList = (PermitedUsersList) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.permitedList = new PermitedUsersList();
        } catch (IOException e) {
            e.printStackTrace();
            this.permitedList = new PermitedUsersList();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getPermitedList() {
        return this.permitedList.getPermitedUsers();
    }

    public void add(User user) {
        if (!this.permitedList.isPermited((user))) {
            this.permitedList.add(user);
            this.updateFile();
        }
    }
}


