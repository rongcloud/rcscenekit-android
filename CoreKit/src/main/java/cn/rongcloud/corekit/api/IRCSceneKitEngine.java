package cn.rongcloud.corekit.api;

import android.content.Context;

import cn.rongcloud.corekit.core.RCKitInit;

/**
 * Created by gyn on 2021/11/15
 */
public interface IRCSceneKitEngine {

    void installKit(RCKitInit... kit);

    void initWithAppKey(Context context, String appKey);

    <T> T getKitConfig(Class<T> c);

    Context getContext();
}
