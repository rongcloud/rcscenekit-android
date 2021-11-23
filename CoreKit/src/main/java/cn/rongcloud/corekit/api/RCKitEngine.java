package cn.rongcloud.corekit.api;

import cn.rongcloud.corekit.core.RCKitEngineImpl;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by hugo on 2021/11/15
 */
public abstract class RCKitEngine implements IRCKitEngine {

    public static IRCKitEngine getInstance() {
        return RCKitEngineImpl.getInstance();
    }

    public static void setDebug(boolean debug) {
        VMLog.setDebug(debug);
    }

}
