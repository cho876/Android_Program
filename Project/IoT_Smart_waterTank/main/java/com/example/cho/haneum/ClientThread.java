package com.example.cho.haneum;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * Created by Cho on 2017-07-18.
 */

/*  클라이언트 소켓 생성을 위한 스레드  */
public class ClientThread extends Thread {
    private BluetoothSocket mmCSocket;
    private BluetoothService bluetoothService;

    // 디바이스와 접속을 위한 클라이언트 소켓 생성
    public ClientThread(BluetoothService service, BluetoothDevice device) {
        this.bluetoothService = service;
        try {
            mmCSocket = device.createInsecureRfcommSocketToServiceRecord(bluetoothService.BLUE_UUID);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void run() {
        try {   // 디바이스와 접속 시도
            mmCSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            try {                    // 접속 실패 시, 소켓을 닫는다.
                mmCSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
        }

        bluetoothService.onConnected(mmCSocket);
    }

    /*  클라이언트 소켓 중지  */
    public void cancel() {
        try {
            mmCSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
