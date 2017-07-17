package com.example.cho.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.os.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MainActivity";
    private final static int REQUEST_ENABLE_BT = 1;
    private Set<BluetoothDevice> pairedDevice;
    private int pairedDeviceCount;
    private Button bSent;
    private EditText ed_content;
    private String sContent;
    private TextView tv_txt;
    private BluetoothAdapter mAdapter;
    private BluetoothDevice mDevice;
    private Thread mWorkerThread;
    private BluetoothSocket mSocket;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private byte mDelimiter;    // 문장 마지막 문자
    private int readBufferPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDelimiter = '.';
        initView();
        checkBT();
    }

    public void initView() {
        bSent = (Button) findViewById(R.id.main_btn);
        bSent.setOnClickListener(this);
        ed_content = (EditText) findViewById(R.id.main_edit);
        tv_txt = (TextView) findViewById(R.id.main_txt);
    }

    public void checkBT() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null) {  // 장치가 블루투스 지원하지 않는 경우
            Toast.makeText(this, "장치가 블루투스를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {             // 장치가 블루투스 지원하는 경우
            Toast.makeText(this, "장치가 블루투스를 지원합니다.", Toast.LENGTH_SHORT).show();
            if (!mAdapter.isEnabled()) {       // 블루투스를 지원하지만 활성화 상태가 아닐 경우
                Intent intentBT = new Intent(mAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentBT, REQUEST_ENABLE_BT);
            }
            selectDevice();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {   // 블루투스가 활성 상태로 변경될 시,
                    Log.e(TAG, "블루투스 활성 상태로 변경됨");
                } else if (resultCode == RESULT_CANCELED) {   // 블루투스가 비활성 상태일 시,
                    Log.e(TAG, "블루투스 비활성 상태");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn:        // 보내기 버튼
                sendData(ed_content.toString());
                break;
        }
    }

    public void selectDevice() {
        pairedDevice = mAdapter.getBondedDevices();
        pairedDeviceCount = pairedDevice.size();

        Toast.makeText(MainActivity.this, "selectDevice", Toast.LENGTH_SHORT).show();
        if (pairedDeviceCount == 0) {   // 페어링된 장치가 없을 경우
            Toast.makeText(MainActivity.this, "페어링된 장치가 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final List<String> listItems = new ArrayList<String>();
        for (BluetoothDevice device : pairedDevice) {
            listItems.add(device.getName().toString());
        }
        listItems.add("취소");  // 맨 마지막 취소 항목 추가

        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == pairedDeviceCount) {  // 취소 버튼 누를 시,
                    finish();
                } else {        // 기기 목록 누를 경우
                    Toast.makeText(MainActivity.this, "item"+item, Toast.LENGTH_SHORT).show();
                    connectToSelectedDevice(listItems.get(item));  // 기기 연결하기
                }
            }
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;
        for (BluetoothDevice device : pairedDevice) {
            if (name.equals(device.getName())) {
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }

    public void connectToSelectedDevice(String selectedDeviceName) {
        mDevice = getDeviceFromBondedList(selectedDeviceName);
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // 소켓 생성
            mSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
            // RFCOMM 채녈을 통한 연결
            mSocket.connect();

            // 데이터 송수신을 위한 스트림 열기
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();

            // 데이터 수신 준비
            beginListenForData();
        } catch (Exception e) {   // 블루투스 연결 중 오류 발생 시,
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            mWorkerThread.interrupt();  // 데이터 수신 쓰레드 종료
            mInputStream.close();
            mOutputStream.close();
            mSocket.close();
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    void sendData(String msg) {
        msg += mDelimiter;
        try {                     // 문자열 전송
            mOutputStream.write(msg.getBytes());
        } catch (Exception e) {    // 전송 중 오류 발생 시,
            finish();
        }
    }

    public void beginListenForData() {
        final Handler handler = new Handler();
        final byte[] readBuffer = new byte[1024];    // 수신 버퍼
        readBufferPosition = 0;  //    버퍼 내 수신 문자 저장 위치

        /*  문자열 수신 쓰레드  */
        mWorkerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        int bytesAvaliable = mInputStream.available();  // 수신 데이터 확인
                        if (bytesAvaliable > 0) {   // 데이터가 수신된 경우
                            byte[] packetBytes = new byte[bytesAvaliable];
                            mInputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvaliable; i++) {
                                byte b = packetBytes[i];
                                if (b == mDelimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable() {
                                        public void run() {
                                            // 수신된 문자열 데이터에 대한 처리 작업
                                            tv_txt.setText(data);
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException e) {  // 데이터 수신 중 오류 발생
                        finish();
                    }
                }
            }
        });
        mWorkerThread.start();
    }
}