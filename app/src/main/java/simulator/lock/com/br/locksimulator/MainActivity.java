package simulator.lock.com.br.locksimulator;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import simulator.lock.com.br.locksimulator.connection.AcceptThread;
import simulator.lock.com.br.locksimulator.models.LockStatus;
import simulator.lock.com.br.locksimulator.models.Session;

public class MainActivity extends AppCompatActivity {
    public static final int ADMIN_LOGGED = 0;
    public static final int ACCESS_GRANTED = 1;
    public static final int OPEN_LOCK = 2;
    public static final int CLOSE_LOCK = 3;
    public static final int CONNECTION_CLOSED = 5;

    private BluetoothAdapter bluetoothAdapter;

    private static Context context;

    private TextView statusTextView;

    private EditText logText;

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ADMIN_LOGGED || msg.what == ACCESS_GRANTED) {
                updateLog((String) msg.obj);
            } else if (msg.what == OPEN_LOCK) {
                updateLog(msg.obj.toString());
                statusTextView.setText(LockStatus.OPENED.getLabel());
            } else if (msg.what == CLOSE_LOCK) {
                updateLog(msg.obj.toString());
                statusTextView.setText(LockStatus.LOCKED.getLabel());
            } else if (msg.what == CONNECTION_CLOSED) {
                bluetoothAdapter.disable();
                while(bluetoothAdapter.isEnabled()) {}
                enableBluetooth();
                AcceptThread acceptThread = new AcceptThread(bluetoothAdapter, handler);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.statusTextView = (TextView) findViewById(R.id.statusText);
        this.statusTextView.setText(LockStatus.LOCKED.getLabel());

        this.logText = (EditText) findViewById(R.id.logText);
        this.logText.setEnabled(false);

        context = getApplicationContext();
        initBluetooth();
    }

    private void initBluetooth() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth não suportado", Toast.LENGTH_LONG).show();
        } else {
            enableBluetooth();
        }
    }

    private void enableBluetooth() {
        if (!this.bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableIntent);

            while (!bluetoothAdapter.isEnabled()) {}
        }
        makeDiscoverable();
        AcceptThread acceptThread = new AcceptThread(this.bluetoothAdapter, handler);
        acceptThread.start();
    }

    private void makeDiscoverable() {
        Intent descovarableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        descovarableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(descovarableIntent);
    }

    public static Context getContext() {
        return context;
    }

    private void updateStatus(LockStatus status) {
        this.statusTextView.setText(status.getLabel());
    }

    private void updateLog(String log) {
        logText.getText().append(log + "\n");
    }

}
