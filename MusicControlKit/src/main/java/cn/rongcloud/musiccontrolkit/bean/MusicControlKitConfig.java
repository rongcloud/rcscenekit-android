package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttribute;

/**
 * Created by gyn on 2021/12/7
 */
public class MusicControlKitConfig implements Serializable {
    @SerializedName("toolBar")
    private MusicToolbarConfig musicToolbar;
    @SerializedName("contentAttribute")
    private RCAttribute contentAttribute;
    @SerializedName("musicList")
    private MusicListConfig musicList;
    @SerializedName("musicAdd")
    private MusicAddConfig musicAdd;
    @SerializedName("musicControl")
    private MusicControlConfig musicControl;
    @SerializedName("soundEffect")
    private SoundEffectConfig soundEffect;

    public MusicToolbarConfig getMusicToolbar() {
        return musicToolbar;
    }

    public void setMusicToolbar(MusicToolbarConfig musicToolbar) {
        this.musicToolbar = musicToolbar;
    }

    public RCAttribute getContentAttribute() {
        return contentAttribute;
    }

    public void setContentAttribute(RCAttribute contentAttribute) {
        this.contentAttribute = contentAttribute;
    }

    public MusicListConfig getMusicList() {
        return musicList;
    }

    public void setMusicList(MusicListConfig musicList) {
        this.musicList = musicList;
    }

    public MusicAddConfig getMusicAdd() {
        return musicAdd;
    }

    public void setMusicAdd(MusicAddConfig musicAdd) {
        this.musicAdd = musicAdd;
    }

    public MusicControlConfig getMusicControl() {
        return musicControl;
    }

    public void setMusicControl(MusicControlConfig musicControl) {
        this.musicControl = musicControl;
    }

    public SoundEffectConfig getSoundEffect() {
        return soundEffect;
    }

    public void setSoundEffect(SoundEffectConfig soundEffect) {
        this.soundEffect = soundEffect;
    }
}
