package cn.rongcloud.chatroomkit;

import android.content.Context;

import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import cn.rongcloud.corekit.annotation.KitInit;
import cn.rongcloud.corekit.api.ICoreKitInit;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by hugo on 2021/11/18
 */
@KitInit
public class ChatRoomKitInit implements ICoreKitInit {
    private static final String TAG = VMLog.getTag(ChatRoomKitInit.class);

    private static Holder holder = new Holder();

    public static ChatRoomKitInit getInstance() {
        return holder.instance;
    }

    @Override
    public void init(Context context) {
        EmojiManager.install(new IosEmojiProvider());
        VMLog.d(TAG, TAG + " has init");
    }

    private static class Holder {
        private ChatRoomKitInit instance = new ChatRoomKitInit();
    }
}
