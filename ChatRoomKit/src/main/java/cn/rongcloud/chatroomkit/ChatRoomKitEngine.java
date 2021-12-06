package cn.rongcloud.chatroomkit;

import android.content.Context;

import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import java.io.File;

import cn.rongcloud.corekit.api.ICoreKitInit;
import cn.rongcloud.corekit.utils.FileUtils;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by hugo on 2021/11/18
 */
public class ChatRoomKitEngine implements ICoreKitInit {
    private static final String TAG = ChatRoomKitEngine.class.getSimpleName();

    private static Holder holder = new Holder();
    private String voicePath;

    public static ChatRoomKitEngine getInstance() {
        return holder.instance;
    }

    @Override
    public void init(Context context) {
        EmojiManager.install(new IosEmojiProvider());
        setVoicePath(context.getFilesDir().getAbsolutePath() + "/rckit_voice");
        VMLog.d(TAG, TAG + " has init");
    }

    /**
     * @return 返回音频文件存储路径
     * 默认data/data/packname/files/rckit_voice
     */
    public String getVoicePath() {
        return voicePath;
    }

    /**
     * 设置音频文件存储路径
     *
     * @param voicePath
     */
    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
        File file = new File(voicePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 清除所有本地音频缓存
     */
    public void clearVoiceCache() {
        FileUtils.delAllFile(voicePath);
    }

    private static class Holder {
        private ChatRoomKitEngine instance = new ChatRoomKitEngine();
    }
}
