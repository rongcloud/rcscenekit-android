package cn.rongcloud.corekit.api;

import android.content.Context;

/**
 * Created by hugo on 2021/11/15
 */
public interface IRCSceneKitEngine {

    void installKit(ICoreKitInit... kit);

    void initWithAppKey(Context context, String appKey);

    <T> T getKitConfig(Class<T> c);

}
