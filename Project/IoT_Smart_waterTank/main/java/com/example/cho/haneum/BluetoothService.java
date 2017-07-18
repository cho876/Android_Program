package com.example.cho.haneum;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Cho on 2017-07-18.
 */

public class BluetoothService{

    public static final int ACTION_ENABLE_BT = 1001;
    public static final String BLUE_NAME = "BluetoothEx";  // 접속시 사용하는 이름
    public static final UUID BLUE_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    private ArrayList<String> mArDevice;   // 디바이스 목록
    private Activity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private ServerThread mSthread = null;  // 서버 소켓 접속 스레드
    private ClientThread mCthread = null;  // 클라이언트 소켓 접속 스레드
    private SocketThread mSocketThread = null;  // 데이터 송수신 스레드


    public BluetoothService(Activity activity) {
        mArDevice = new ArrayList<String>();
        mActivity = activity;
    }

    /*  블루투스 사용 가능상태 판단  */
    public boolean canUseBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();  // 블루투스 어댑터 생성

        if (mBluetoothAdapter == null) {              // 블루투스를 지원하지 않는 경우
            Toast.makeText(mActivity, "블루투스를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!mBluetoothAdapter.isEnabled()) {      // 블루투스를 지원하지만 활성화되지 않았을 경우
                Intent intentBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mActivity.startActivityForResult(intentBT, ACTION_ENABLE_BT);
                return false;
            }
        }
        return true;
    }

    /*  페어링된 디바이스 목록 구하기  */
    public void getParedDevice() {
        if (mSthread != null)
            return;
        mSthread = new ServerThread(this, mBluetoothAdapter);
        mSthread.start();   // 서버 소켓 접속을 위한 스레드 생성 & 시작

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity.getApplicationContext());
        builder.setTitle("블루투스 장치 선택");
        // 블루투스 어뎁터에서 페어링된 디바이스 목록 Get
        Set<BluetoothDevice> deviceSet = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : deviceSet) {
            mArDevice.add(device.getName() + " - " + device.getAddress());
        }
        setDiscoverable();

        final CharSequence[] items = mArDevice.toArray(new CharSequence[mArDevice.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                connectToSelectedDevices(which);
            }
        });
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // 디바이스 검색 중지
    public void stopFindDevice() {

        // 현재 디바이스 검색 중이라면 취소한다
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    /*  디바이스와 연결  */
    public void connectToSelectedDevices(int index) {
        // 사용자 선택 항목 Get
        String strItem = mArDevice.get(index);

        // 사용자가 선택한 디바이스 주소 Get
        int pos = strItem.indexOf(" - ");
        if (pos <= 0) return;
        String address = strItem.substring(pos + 3);

        // 디바이스 검색 중지
        stopFindDevice();
        mSthread.cancel();
        mSthread = null;

        if (mCthread != null) return;
        // 상대 디바이스 Get
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // 클라이언트 소켓 스레드 생성 및 시작
        mCthread = new ClientThread(this, device);
        mCthread.start();
        mSocketThread.write("111");
    }

    public void sendMsg(String strMsg){
        Message msg = Message.obtain(mHandler, 0, strMsg);
        mHandler.sendMessage(msg);
    }

    // 메시지 화면 출력을 위한 핸들러
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String strMsg = (String) msg.obj;
                Toast.makeText(mActivity, strMsg, Toast.LENGTH_SHORT).show();
            }
        }
    };

    // 다른 디바이스에게 자신을 검색 허용
    public void setDiscoverable() {
        // 현재 검색 허용 상태라면 함수 탈출
        if (mBluetoothAdapter.getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
            return;
        // 다른 디바이스에게 자신을 검색 허용 지정
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        mActivity.startActivity(intent);
    }

    public void onConnected(BluetoothSocket socket) {
        // 데이터 송수신 스레드가 생성되어 있다면 삭제
        if (mSocketThread != null)
            mSocketThread = null;
        //데이터 송수신 스레드 시작
        mSocketThread = new SocketThread(this, socket);
        mSocketThread.start();
    }


}
