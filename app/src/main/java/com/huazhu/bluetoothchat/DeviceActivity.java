package com.huazhu.bluetoothchat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class DeviceActivity extends AppCompatActivity {

    private ListView pairedListView;
    private ListView findListView;
    private Button scanButton;
    private Adapter pairedAdapter;
    private Adapter findAdapter;
    private String[] paridItems;
    private String[] finddItems;
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;

    public DeviceActivity() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        pairedListView = (ListView) findViewById(R.id.paired_list_view);
        findListView = (ListView) findViewById(R.id.find_list_view);
        scanButton = (Button) findViewById(R.id.scan_button);
        pairedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                paridItems);
        findAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                finddItems);
    }


    public void setUpBluetooth() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, "该设备不支持蓝牙", Toast.LENGTH_SHORT).show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mContext.startActivity(enableBtIntent);
            }
        }
    }

    // 开始扫描蓝牙，并且使自己的设备可见
    public void scanDevies() {
        setUpBluetooth();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(mReceiver, filter);
        mBluetoothAdapter.startDiscovery();
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        mContext.startActivity(discoverableIntent);
    }

    public void getPairedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
            }
        }
    }

    public void connectDevice() {

    }

    /**
     * The BroadcastReceiver that listens for discovered devices and changes the title when
     * discovery is finished
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> mNewDevices = new ArrayList<String>();
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevices.add(device.getName() + "\n" + device.getAddress());
                }
                // when discovery is finished,change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            }
        }
    };

    public void UnregisterReceiver() {
        mContext.unregisterReceiver(mReceiver);
    }
}
