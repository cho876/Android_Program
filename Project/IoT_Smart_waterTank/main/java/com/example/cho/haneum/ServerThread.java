package com.example.cho.haneum;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * Created by Cho on 2017-07-18.
 */

public class ServerThread extends Thread {
    private BluetoothService bluetoothService;
    private BluetoothServerSocket mSSocket;
    private BluetoothAdapter mBA;

    // 서버 소켓 생성
    public ServerThread(BluetoothService service, BluetoothAdapter mBA) {
        bluetoothService = service;
        this.mBA = mBA;

        try {
            mSSocket = mBA.listenUsingRfcommWithServiceRecord(BluetoothService.BLUE_NAME, BluetoothService.BLUE_UUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        BluetoothSocket socket = null;

        // 디바이스에서 접속을 요청할 때까지 대기
        try {
            socket = mSSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 디바이스와 접속되었을 시, 데이터 송수신 스레드 시작
        bluetoothService.onConnected(socket);
    }

    // 서버 소켓 중지
    public void cancel() {
        try {
            mSSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
