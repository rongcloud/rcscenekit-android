package cn.rongcloud.kitdemo;

import android.app.Application;

import cn.rongcloud.chatroomkit.ChatRoomKitInit;
import cn.rongcloud.corekit.api.RCSceneKitEngine;

/**
 * Created by hugo on 2021/11/15
 */
public class MyApplication extends Application {

    public static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        RCSceneKitEngine.getInstance().installKit(ChatRoomKitInit.getInstance());
        RCSceneKitEngine.getInstance().initWithAppKey(this, "");
    }
}
