package simulator.lock.com.br.locksimulator.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class AcceptThread extends Thread{

    private final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private final BluetoothServerSocket serverSocket;
    private Handler handler;

    public AcceptThread(BluetoothAdapter bluetoothAdapter, Handler handler) {
        this.handler = handler;
        BluetoothServerSocket _tmp = null;
        try {
            _tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Lock", MY_UUID_SECURE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocket = _tmp;
    }

    @Override
    public void run() {
        BluetoothSocket socket;

        while(true) {
            try {
                socket = serverSocket.accept();
                if (socket != null) {
                    serverSocket.close();
                    CommunicationThread communicationThread = new CommunicationThread(socket, handler);
                    communicationThread.start();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
