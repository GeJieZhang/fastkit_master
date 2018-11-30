package com.example.fastkit.service.protect;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sibao.ProcessConnection;


/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/4/7.
 * Version 1.0
 * Description: 守护进程，双进程通信
 */
public class GuardService extends Service {

    private final int GuardId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 提高进程优先级
        startForeground(GuardId, new Notification());

        // 绑定简历链接
        bindService(new Intent(this, MessageService.class), mServiceConnection, Context.BIND_IMPORTANT);

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
            //            Log.e("========MessageService:", "建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            //            Log.e("========MessageService:", "断开连接");
            // 断开链接 ,重新启动，重新绑定
            startService(new Intent(GuardService.this, MessageService.class));

            bindService(new Intent(GuardService.this, MessageService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();


        unbindService(mServiceConnection);
    }
}
