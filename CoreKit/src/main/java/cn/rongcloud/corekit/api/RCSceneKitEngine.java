package cn.rongcloud.corekit.api;

import cn.rongcloud.corekit.core.RCSceneKitEngineImpl;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by gyn on 2021/11/15
 */
public abstract class RCSceneKitEngine implements IRCSceneKitEngine {

    public static IRCSceneKitEngine getInstance() {
        return RCSceneKitEngineImpl.getInstance();
    }

    public static void setDebug(boolean debug) {
        VMLog.setDebug(debug);
    }

}
