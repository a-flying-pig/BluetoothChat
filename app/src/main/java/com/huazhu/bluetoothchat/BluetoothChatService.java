package com.huazhu.bluetoothchat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Administrator on 2017-5-20.
 */
public class BluetoothChatService {
    private static final String TAG = "BluetoothChatService";
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> findBtDevices;
    public BluetoothChatService(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
        Set<BluetoothDevice> pairedDevicies = mBluetoothAdapter.getBondedDevices();
        if (pairedDevicies.size() > 0) {
            for (BluetoothDevice device : pairedDevicies) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                int deviceClass = device.getType();
            }
        }
    }

    public void connectDevice() {

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            findBtDevices.add(device);
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress();
            int deviceClass = device.getType();
        }
    };

    public void UnregisterReceiver() {
        mContext.unregisterReceiver(mReceiver);
    }

    private class AcceptThread extends Thread {
        private  final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("NAME",
                        new UUID());
            } catch (IOException e) {
                Log.e(TAG, "Socket's listen() method failed", e);
            }
            mmServerSocket = tmp;
        }

        @Override
        public void run() {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.d(TAG, "Socket's accept() method failed", e);
                    break;
                }

                if (socket != null) {
                    manageMyConnectedSocket(socket);
                    mmServerSocket.close();
                    break;
                }
            }
        }

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }

    }
}
