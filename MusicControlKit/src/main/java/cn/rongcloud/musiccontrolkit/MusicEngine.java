package cn.rongcloud.musiccontrolkit;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.utils.ListUtil;
import cn.rongcloud.corekit.utils.VMLog;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.iinterface.AbsMusicEngine;

/**
 * Created by hugo on 2021/12/1
 * 音乐列表控制类
 */
public class MusicEngine extends AbsMusicEngine {
    private static final String TAG = MusicEngine.class.getSimpleName();
    private static final Holder holder = new Holder();

    /**
     * 播放列表
     */
    private final MutableLiveData<List<Music>> musicListLiveData;
    /**
     * 当前播放的音乐
     */
    private Music playingMusic;
    /**
     * 是否正在播放
     */
    private MutableLiveData<Boolean> playingLiveData;
    /**
     * 播放列表的 id
     */
    private MutableLiveData<List<String>> musicIdListLiveData;

    public MusicEngine() {
        musicListLiveData = new MutableLiveData<>();
        playingLiveData = new MutableLiveData<>();
        musicIdListLiveData = new MutableLiveData<>();
    }

    /**
     * @return 音乐列表单例
     */
    public static MusicEngine getInstance() {
        return holder.instance;
    }

    /**
     * @return 音乐列表数据监听
     */
    public MutableLiveData<List<Music>> musicListObserve() {
        return musicListLiveData;
    }

    /**
     * @return 刷新整个列表
     */
    public MutableLiveData<Boolean> playingObserve() {
        return playingLiveData;
    }

    /**
     * @return 音乐列表id集合变化监听
     */
    public MutableLiveData<List<String>> musicIdListObserve() {
        return musicIdListLiveData;
    }

    private List<String> getMusicIdList() {
        return musicIdListLiveData.getValue();
    }

    private void setMusicIdList(List<String> musicIdList) {
        musicIdListLiveData.postValue(musicIdList);
    }

    /**
     * @param music 添加音乐
     */
    public void addMusic(Music music) {
        VMLog.e(TAG, "==============");
        List<Music> list = getMusicList();
        if (isInMusicList(music.getMusicId())) {
            return;
        }
        list.add(music);
        setMusicList(list);
    }

    /**
     * 播放音乐
     *
     * @param music
     */
    public void playMusic(Music music) {
        if (music != null) {
            if (!isInMusicList(music.getMusicId())) {
                addMusic(music);
            }
            playingMusic = music;
            setPlayingLiveData(true);
        }
    }

    /**
     * 播放音乐列表的下一首
     */
    public Music playNextMusic() {
        List<Music> musicList = getMusicList();
        if (ListUtil.isEmpty(musicList)) {
            return null;
        }
        if (playingMusic != null) {
            int index = musicList.indexOf(playingMusic);
            if (index >= musicList.size() - 1) {
                playingMusic = musicList.get(0);
            } else {
                playingMusic = musicList.get(index + 1);
            }
        } else {
            playingMusic = musicList.get(0);
        }
        setPlayingLiveData(true);
        return playingMusic;
    }

    /**
     * @param musicList 批量添加音乐
     */
    public void addMusicList(List<Music> musicList) {
        List<Music> list = getMusicList();
        list.addAll(musicList);
        setMusicList(list);
    }

    /**
     * 置顶音乐
     * 这里置顶的逻辑是：把要操作的歌曲移到正在播放歌曲的下面，
     * 如果当前没有正在播放的歌曲则播放该歌曲，位置不变化
     *
     * @param music 要移动的music
     */
    public void topMusic(Music music) {
        if (playingMusic != null && isPlaying()) {
            List<Music> newMusicList = getMusicList();
            int downToIndex = newMusicList.indexOf(playingMusic);
            int index = newMusicList.indexOf(music);
            newMusicList.remove(index);
            if (index > downToIndex) {
                newMusicList.add(downToIndex + 1, music);
            } else {
                newMusicList.add(downToIndex, music);
            }
            setMusicList(newMusicList);
            onTopMusic(music, playingMusic);
        } else {
            playingMusic = music;
            setPlayingLiveData(true);
        }
    }

    /**
     * 从列表删除音乐
     *
     * @param music 要删除的音乐
     */
    public void deleteMusic(Music music) {
        if (music == null) {
            return;
        }
        List<Music> list = getMusicList();
        list.remove(music);
        setMusicList(list);
        onDeleteMusic(music);
    }

    /**
     * @return 获取音乐数据
     */
    public List<Music> getMusicList() {
        List<Music> musicList = musicListLiveData.getValue();
        if (musicList == null) {
            musicList = new ArrayList<>();
        }
        return musicList;
    }

    /**
     * @param musicList 设置列表数据
     */
    public void setMusicList(List<Music> musicList) {
        musicListLiveData.postValue(musicList);
        List<String> musicIdList = new ArrayList<>();
        if (ListUtil.isNotEmpty(musicList)) {
            for (Music music : musicList) {
                musicIdList.add(music.getMusicId());
            }
        }
        setMusicIdList(musicIdList);
    }

    /**
     * 是否是正在播放的音乐
     *
     * @param music
     * @return
     */
    public boolean isPlayingMusic(Music music) {
        if (playingMusic == null) {
            return false;
        } else {
            return TextUtils.equals(music.getMusicId(), playingMusic.getMusicId());
        }
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        if (playingLiveData.getValue() != null) {
            return playingLiveData.getValue();
        }
        return false;
    }

    public Music getPlayingMusic() {
        return playingMusic;
    }

    /**
     * 点击音乐，执行播放或暂停
     *
     * @param music 操作的音乐
     */
    public void toggleMusic(Music music) {
        if (playingMusic == null) {
            playingMusic = music;
            setPlayingLiveData(true);
        } else if (TextUtils.equals(playingMusic.getMusicId(), music.getMusicId())) {
            boolean isPlaying = isPlaying();
            if (isPlaying) {
                onPauseMixingWithMusic(music);
                playingLiveData.postValue(false);
            } else {
                onResumeMixingWithMusic(music);
                playingLiveData.postValue(true);
            }
        } else {
            playingMusic = music;
            setPlayingLiveData(true);
        }
    }

    private void setPlayingLiveData(boolean isPlaying) {
        if (isPlaying) {
            onStartMixingWithMusic(playingMusic);
        } else {
            onPauseMixingWithMusic(playingMusic);
        }
        playingLiveData.postValue(isPlaying);
    }

    /**
     * 重置监听和数据
     */
    public void release() {
        setPlayingLiveData(false);
        setMusicList(new ArrayList<>());
        playingMusic = null;
        super.release();
    }

    /**
     * 检查音乐是否已经在列表
     *
     * @param musicId
     * @return
     */
    public boolean isInMusicList(String musicId) {
        return getMusicIdList().contains(musicId);
    }

    /**
     * 根据id获取 music
     *
     * @param musicId
     * @return
     */
    public Music getMusic(String musicId) {
        if (TextUtils.isEmpty(musicId)) {
            return null;
        }
        List<Music> musicList = getMusicList();
        for (Music music : musicList) {
            if (TextUtils.equals(musicId, music.getMusicId())) {
                return music;
            }
        }
        return null;
    }

    private static class Holder {
        private MusicEngine instance = new MusicEngine();
    }
}
