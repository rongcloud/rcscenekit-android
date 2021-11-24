package cn.rongcloud.musiccontrolkit;

import android.content.Context;

import cn.rongcloud.corekit.api.ICoreKitInit;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by hugo on 2021/11/23
 */
class MusicControlKitInit implements ICoreKitInit {

    private static final String TAG = MusicControlKitInit.class.getSimpleName();

    private static Holder holder = new Holder();

    public static MusicControlKitInit getInstance() {
        return holder.instance;
    }

    @Override
    public void init(Context context) {
        VMLog.d(TAG, TAG + " has init");
    }

    private static class Holder {
        private MusicControlKitInit instance = new MusicControlKitInit();
    }
}
