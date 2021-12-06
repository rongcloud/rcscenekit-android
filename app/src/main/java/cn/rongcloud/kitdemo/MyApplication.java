package cn.rongcloud.kitdemo;

import android.app.Application;

import cn.rongcloud.chatroomkit.ChatRoomKitInit;
import cn.rongcloud.corekit.api.RCSceneKitEngine;
import cn.rongcloud.voiceroom.api.RCVoiceRoomEngine;
import cn.rongcloud.voiceroom.api.callback.RCVoiceInitCallback;

/**
 * Created by hugo on 2021/11/15
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    public static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        // 注册使用的 kit，可以传入多个 Kit
        RCSceneKitEngine.getInstance().installKit(ChatRoomKitInit.getInstance());
        // 初始化 Kit，优先根据 appkey 从远端下载配置，不成功采用默认配置
        RCSceneKitEngine.getInstance().initWithAppKey(this, "appkey");


        // demo 的依赖初始化
        // 初始化语聊房SDK
        RCVoiceRoomEngine.getInstance().initWithAppKey(this, "kj7swf8ok3052", new RCVoiceInitCallback() {
            @Override
            public void onInitComplete() {

            }
        });
//        // 模拟一个用户创建一个连接
//        RCVoiceRoomEngine.getInstance().connectWithToken("ECuzpyIw7VgP24aAzBQb20oAGVeArlCAA8D2xIQZIrw=@7cp1.cn.rongnav.com;7cp1.cn.rongcfg.com", new RCVoiceRoomCallback() {
//            @Override
//            public void onSuccess() {
//                VMLog.d(TAG, "连接成功");
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                VMLog.e(TAG, "连接失败 " + i + ":" + s);
//            }
//        });
    }

}
