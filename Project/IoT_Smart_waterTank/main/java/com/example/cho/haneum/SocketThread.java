package com.example.cho.haneum;

import android.bluetooth.BluetoothSocket;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Cho on 2017-07-18.
 */

/*  데이터 송수신 Thread  */
public class SocketThread extends Thread {
    private final BluetoothSocket mmSocket;  // 클라이언트 소켓
    private InputStream mmInputStream;       // 입력 스트림
    private OutputStream mmOutputStream;     // 출력 스트림
    private BluetoothService bluetoothService;

    public SocketThread(BluetoothService service, BluetoothSocket socket) {
        mmSocket = socket;
        bluetoothService = service;

        // 입력 스트림과 출력 스트림 get
        try {
            mmInputStream = socket.getInputStream();
            mmOutputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*  소켓에 수신된 데이터 화면 표시 */
    public void run(){
        byte[] buffer = new byte[1024];
        int bytes;

        while(true){
            try{
                // 입력 스트림에서 데이터 read
                bytes = mmInputStream.read(buffer);
                String strBuf = new String(buffer, 0, bytes);
                Log.e("Receive", strBuf);
                SystemClock.sleep(1);
            }catch(IOException e){
                e.printStackTrace();
                break;
            }
        }
    }

    /*  데이터를 소켓으로 전송  */
    public void write(String strBuf){
        try{
            // 출력 스트림에 데이터 저장
            byte[] buffer = strBuf.getBytes();
            mmOutputStream.write(buffer);
            Log.e("Send", strBuf);
            bluetoothService.sendMsg(strBuf);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
