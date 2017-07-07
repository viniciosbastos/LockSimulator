package simulator.lock.com.br.locksimulator.models;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import simulator.lock.com.br.locksimulator.MainActivity;

/**
 * Created by Vinicios on 04/07/2017.
 */

public class AdminStorage{

    private String FILE_NAME = "admin_storage.as";

    private AdminUsersList adminList;

    private static AdminStorage instance;

    private AdminStorage() {
        readFile();
    }

    public static AdminStorage getInstance() {
        if (instance == null)
            instance = new AdminStorage();
        return instance;
    }

    private void updateFile() {
        try {
            FileOutputStream fos = MainActivity.getContext().openFileOutput(this.FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.adminList);
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
            this.adminList = (AdminUsersList) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.adminList = new AdminUsersList();
            this.adminList.add(new User("admin   ", "admin123"));
        } catch (IOException e) {
            e.printStackTrace();
            this.adminList = new AdminUsersList();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAdminList() {
        return this.adminList.getAdminUsers();
    }

    public void add(User user) {
        this.adminList.add(user);
        updateFile();
    }
}
