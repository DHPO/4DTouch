package com.example.xzt.pdfreader;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);

        Button scanButton=(Button)findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Device doesnt Support Bluetooth", Toast.LENGTH_SHORT).show();
                }
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableAdapter, 0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                final Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
                final List<String> devices = new ArrayList<String>();
                if (bondedDevices.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Pair the Device first", Toast.LENGTH_SHORT).show();
                } else {
                    for (BluetoothDevice device : bondedDevices) {
                        devices.add(device.getName());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(BluetoothConnectActivity.this, android.R.layout.simple_list_item_1, devices);
                ListView listView = (ListView) findViewById(R.id.list_view);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String targetDevice=devices.get(position);
                        for (BluetoothDevice device : bondedDevices) {
                            if(device.getName().equals(targetDevice)){
                                final Intent serviceIntent=new Intent(BluetoothConnectActivity.this,BluetoothConnectService.class);
                                serviceIntent.putExtra("device",device);
                                startService(serviceIntent);
                                Intent intent=new Intent(BluetoothConnectActivity.this,UserSettingActivity.class);
                                startActivityForResult(intent,1);
                            }
                        }
                    }
                });
            }
        });
        Button stopButton=(Button)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                BluetoothConnectService.stopFlag=true;
                Toast.makeText(getApplicationContext(), "Connection closed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
