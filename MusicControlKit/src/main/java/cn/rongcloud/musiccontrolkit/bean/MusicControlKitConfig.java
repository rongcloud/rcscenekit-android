package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttributes;
import cn.rongcloud.corekit.bean.RCNode;

/**
 * Created by gyn on 2021/12/7
 */
public class MusicControlKitConfig implements Serializable {
    @SerializedName("toolBar")
    private RCNode<MusicToolbarConfig> musicToolbar;
    @SerializedName("contentAttributes")
    private RCNode<RCAttributes> contentAttributes;
    @SerializedName("musicList")
    private RCNode<MusicListConfig> musicList;
    @SerializedName("musicAdd")
    private RCNode<MusicAddConfig> musicAdd;
    @SerializedName("musicControl")
    private RCNode<MusicControlConfig> musicControl;
    @SerializedName("soundEffect")
    private RCNode<SoundEffectConfig> soundEffect;

    public MusicToolbarConfig getMusicToolbar() {
        return musicToolbar.getValue();
    }

    public RCAttributes getContentAttributes() {
        return contentAttributes.getValue();
    }

    public MusicListConfig getMusicList() {
        return musicList.getValue();
    }

    public MusicAddConfig getMusicAdd() {
        return musicAdd.getValue();
    }

    public MusicControlConfig getMusicControl() {
        return musicControl.getValue();
    }

    public SoundEffectConfig getSoundEffect() {
        return soundEffect.getValue();
    }

}
