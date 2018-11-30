package com.example.fastkit.service.protect;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sibao.ProcessConnection;


/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/4/7.
 * Version 1.0
 * Description: QQ聊天通讯  代码需要轻
 */
public class MessageService extends Service {
    private final String ACTION1 = "cn.jpush.android.intent.REGISTRATION";
    private final String ACTION2 = "cn.jpush.android.intent.MESSAGE_RECEIVED";
    private final String ACTION3 = "cn.jpush.android.intent.NOTIFICATION_RECEIVED";
    private final String ACTION4 = "cn.jpush.android.intent.NOTIFICATION_OPENED";
    private final String ACTION5 = "cn.jpush.android.intent.CONNECTION";


    private final int MessageId = 1;


    @Override
    public void onCreate() {
        super.onCreate();
        // 创建一个IntentFilter对象，将其action指定为BluetoothDevice.ACTION_FOUND
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION1);
        intentFilter.addAction(ACTION2);
        intentFilter.addAction(ACTION3);
        intentFilter.addAction(ACTION4);
        intentFilter.addAction(ACTION5);
        //Log.e("========MessageService:", "注册广播");

        //在此开启要守护的service



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 提高进程优先级
        startForeground(MessageId, new Notification());

        // 绑定建立链接
        bindService(new Intent(this, GuardService.class), mServiceConnection, Context.BIND_IMPORTANT);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
        };
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 链接上
            //Log.e("========MessageService:", "建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 断开链接 ,重新启动，重新绑定

            // Log.e("========MessageService:", "断开连接");
            startService(new Intent(MessageService.this, GuardService.class));

            bindService(new Intent(MessageService.this, GuardService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
