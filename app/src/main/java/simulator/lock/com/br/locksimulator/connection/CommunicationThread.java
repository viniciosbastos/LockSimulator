package simulator.lock.com.br.locksimulator.connection;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import simulator.lock.com.br.locksimulator.MainActivity;
import simulator.lock.com.br.locksimulator.handlers.CommandProcessor;
import simulator.lock.com.br.locksimulator.handlers.IHandler;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class CommunicationThread extends Thread{

    private BluetoothSocket socket;
    private final InputStream inputstream;
    private final OutputStream outputStream;
    private byte[] buffer;
    private Handler handler;

    public CommunicationThread(BluetoothSocket socket, Handler handler) {
        this.socket = socket;
        this.buffer = new byte[128];
        InputStream _inputstream = null;
        OutputStream _outputStream = null;
        try {
             _inputstream = socket.getInputStream();
             _outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.inputstream = _inputstream;
        this.outputStream = _outputStream;

        this.handler = handler;
    }

    @Override
    public void run() {
        byte[] command;

        while(true) {
            command = read();
            if (command == null) {
                interrupt();
                break;
            }
            proccessCommand(command);
        }
    }

    private void proccessCommand(byte[] command) {
        IHandler commandHandler = CommandProcessor.HandlerFactory(command, this.handler);
        write(commandHandler.getResponse());
    }

    private byte[] read() {
        byte[] command=  null;
        try {
            int bytes = inputstream.read(buffer);
            command = new byte[bytes];
            System.arraycopy(buffer, 0, command, 0, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            Message msg = this.handler.obtainMessage();
            msg.what = MainActivity.CONNECTION_CLOSED;
            msg.sendToTarget();
        }
        return command;
    }

    private void write(byte[] command) {
        try {
            this.outputStream.write(command);
        } catch (IOException e) {
            e.printStackTrace();
            Message msg = this.handler.obtainMessage();
            msg.what = MainActivity.CONNECTION_CLOSED;
            msg.sendToTarget();
            Thread.currentThread().interrupt();
        }
    }
}
