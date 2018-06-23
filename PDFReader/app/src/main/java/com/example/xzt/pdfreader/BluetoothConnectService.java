package com.example.xzt.pdfreader;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothConnectService extends Service {

    private BluetoothSocket socket;
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private OutputStream outputStream;
    private InputStream inputStream;
    private int threshold=600;
    byte buffer[];
    public static boolean stopFlag=false;

    public BluetoothConnectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stopFlag=false;
        BluetoothDevice device=intent.getParcelableExtra("device");
        if(connect(device))
        {
            Toast.makeText(getApplicationContext(), "Connection established.", Toast.LENGTH_SHORT).show();
            beginListenForData();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Connection failed.", Toast.LENGTH_SHORT).show();
            stopSelf(startId);
        }
        return START_STICKY;
    }

    public boolean connect(BluetoothDevice device)
    {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return connected;
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        buffer = new byte[1024];
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopFlag)
                {
                    try
                    {
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String s=new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    int len=s.length();
                                    for(int i=0;i<len;i++){
                                        if(s.charAt(i)=='\r'){//13 19
                                            i++;
                                            assert s.charAt(i)==19:"message wrong!";
                                            int p= Integer.parseInt(Data.buffer);
                                            Data.buffer="";
                                            if(p>threshold){
                                                Data.isFingerPress[Data.index]=true;
                                            }
                                            else{
                                                Data.isFingerPress[Data.index]=false;
                                            }
                                            Data.index=0;
                                        }
                                        else if(s.charAt(i)==19){
                                            continue;
                                        }
                                        else if(s.charAt(i)=='a'){
                                            int p= Integer.parseInt(Data.buffer);
                                            Data.buffer="";
                                            if(p>threshold){
                                                Data.isFingerPress[Data.index]=true;
                                            }
                                            else{
                                                Data.isFingerPress[Data.index]=false;
                                            }
                                            Data.index++;
                                        }
                                        else{
                                            Data.buffer+=s.charAt(i);
                                        }
                                    }

                                }
                            });

                        }
                    }
                    catch (IOException ex)
                    {
                        stopFlag = true;
                    }
                }
                try {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                }
                catch (IOException e){

                }
                stopSelf();
            }
        });

        thread.start();
    }

}
