package cn.rongcloud.corekit.api;

import android.content.Context;

/**
 * Created by gyn on 2021/11/15
 */
public interface IRCSceneKitEngine {

    void initWithAppKey(Context context, String appKey);

    Context getContext();
}
