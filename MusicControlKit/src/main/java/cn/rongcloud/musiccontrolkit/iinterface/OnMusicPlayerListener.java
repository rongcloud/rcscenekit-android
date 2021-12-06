package cn.rongcloud.musiccontrolkit.iinterface;

import cn.rongcloud.musiccontrolkit.bean.Effect;
import cn.rongcloud.musiccontrolkit.bean.Music;

/**
 * Created by hugo on 2021/11/26
 */
public interface OnMusicPlayerListener {
    /**
     * @param localVolume 调整本地音量
     */
    void onLocalVolumeChanged(int localVolume);

    /**
     * @param remoteVolume 调整远端音量
     */
    void onRemoteVolumeChanged(int remoteVolume);

    /**
     * @param micVolume 调整麦克风音量
     */
    void onMicVolumeChanged(int micVolume);

    /**
     * @param earsBackEnable 是否开启耳返
     */
    void onEarsBackEnableChanged(boolean earsBackEnable);

    /**
     * @param music 开始混音
     */
    void onStartMixingWithMusic(Music music);

    /**
     * @param music 恢复混音
     */
    void onResumeMixingWithMusic(Music music);

    /**
     * @param music 暂停混音
     */
    void onPauseMixingWithMusic(Music music);

    /**
     * @param effect 播放氛围
     */
    void onPlayEffect(Effect effect);
}
