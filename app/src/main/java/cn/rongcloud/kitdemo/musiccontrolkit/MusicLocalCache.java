package cn.rongcloud.kitdemo.musiccontrolkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.utils.GsonUtil;
import cn.rongcloud.corekit.utils.ListUtil;
import cn.rongcloud.kitdemo.MyApplication;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.bean.MusicControl;

/**
 * Created by gyn on 2021/11/30
 * 音乐列表缓存类，用户可以从服务端获取，
 * 这里存本地模拟服务端获取
 */
public class MusicLocalCache {
    private static final String SP_MUSIC_LIST = "rckit_music_list";
    private static final String KEY_MUSIC_LIST = "key_music_list";
    private static final String KEY_VOLUME = "key_music_volume";
    private static MusicLocalCache instance;
    private final List<Music> musicList;
    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public MusicLocalCache() {
        musicList = new ArrayList<>();
        sharedPreferences = MyApplication.app.getSharedPreferences(SP_MUSIC_LIST, Context.MODE_MULTI_PROCESS);
        editor = sharedPreferences.edit();
    }

    public static MusicLocalCache getInstance() {
        if (instance == null) {
            instance = new MusicLocalCache();
        }
        return instance;
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    public List<Music> getMusicList() {
        musicList.clear();
        List<Music> list = getMusicListFromSP();
        if (ListUtil.isNotEmpty(list)) {
            musicList.addAll(list);
        }
        return musicList;
    }

    /**
     * 保存音乐数据
     *
     * @param musicList
     */
    public void saveMusicList(List<Music> musicList) {
        editor.putString(KEY_MUSIC_LIST, GsonUtil.obj2Json(musicList));
        editor.commit();
    }

    /**
     * 从本地加载音乐json数据
     *
     * @return
     */
    private List<Music> getMusicListFromSP() {
        String json = sharedPreferences.getString(KEY_MUSIC_LIST, "");
        if (!TextUtils.isEmpty(json)) {
            return GsonUtil.json2List(json, Music.class);
        }
        return null;
    }

    /**
     * 保存音乐控制
     *
     * @param musicControl
     */
    public void saveMusicControl(MusicControl musicControl) {
        editor.putString(KEY_VOLUME, GsonUtil.obj2Json(musicControl));
        editor.commit();
    }

    /**
     * 获取控制
     *
     * @return
     */
    public MusicControl getMusicControl() {
        String json = sharedPreferences.getString(KEY_VOLUME, "");
        if (!TextUtils.isEmpty(json)) {
            return GsonUtil.json2Obj(json, MusicControl.class);
        }
        return new MusicControl();
    }
}
