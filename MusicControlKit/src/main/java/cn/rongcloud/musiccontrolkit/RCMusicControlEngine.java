package cn.rongcloud.musiccontrolkit;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.rongcloud.corekit.api.RCSceneKitEngine;
import cn.rongcloud.corekit.utils.ListUtil;
import cn.rongcloud.corekit.utils.RealPathFromUriUtils;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.iinterface.AbsMusicEngine;
import cn.rongcloud.musiccontrolkit.iinterface.RCMusicKitListener;

/**
 * Created by gyn on 2021/12/1
 * 音乐列表控制类
 */
public class RCMusicControlEngine extends AbsMusicEngine {
    private static final String TAG = RCMusicControlEngine.class.getSimpleName();
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

    private MusicControlDialog musicControlDialog;

    public RCMusicControlEngine() {
        musicListLiveData = new MutableLiveData<>();
        playingLiveData = new MutableLiveData<>();
        musicIdListLiveData = new MutableLiveData<>();
    }

    /**
     * @return 音乐列表单例
     */
    public static RCMusicControlEngine getInstance() {
        return holder.instance;
    }

    public void showDialog(FragmentManager fragmentManager, RCMusicKitListener rcMusicKitListener) {
        setListener(rcMusicKitListener);
        musicControlDialog = new MusicControlDialog();
        musicControlDialog.show(fragmentManager, MusicControlDialog.TAG);
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
     * 默认不循环播放
     */
    public Music playNextMusic() {
        return playNextMusic(false);
    }

    /**
     * 播放音乐列表的下一首
     *
     * @param loop 是否循环
     */
    public Music playNextMusic(boolean loop) {
        List<Music> musicList = getMusicList();
        if (ListUtil.isEmpty(musicList)) {
            return null;
        }
        if (playingMusic != null) {
            int index = musicList.indexOf(playingMusic);
            if (index >= musicList.size() - 1) {
                if (loop) {
                    playingMusic = musicList.get(0);
                } else {
                    stopMusic();
                    onStopMixingWithMusic();
                    return null;
                }
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
     * 暂停播放
     */
    public void pauseMusic() {
        if (playingMusic != null) {
            onPauseMixingWithMusic(playingMusic);
        }
        playingLiveData.postValue(false);
    }

    /**
     * 停止播放
     */
    public void stopMusic() {
        playingMusic = null;
        playingLiveData.postValue(false);
        onStopMixingWithMusic();
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
        if (music == null) {
            return;
        }
        if (playingMusic == null) {
            playingMusic = music;
            setPlayingLiveData(true);
        } else if (TextUtils.equals(playingMusic.getMusicId(), music.getMusicId())) {
            boolean isPlaying = isPlaying();
            if (isPlaying) {
                pauseMusic();
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
        playingLiveData.postValue(false);
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

    public Music parseLocalMusic(Uri uri) {
        if (uri == null) {
            return null;
        }
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            Context context = RCSceneKitEngine.getInstance().getContext();
            mmr.setDataSource(context, uri);
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
//            VMLog.d(TAG, "title:" + title);
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
//            VMLog.d(TAG, "album:" + album);
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
//            VMLog.d(TAG, "artist:" + artist);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
//            VMLog.d(TAG, "duration:" + duration);
            mmr.release();
            // 图片，可以通过BitmapFactory.decodeByteArray转换为bitmap图片
//            byte[] pic = mmr.getEmbeddedPicture();
            String path = RealPathFromUriUtils.getRealPathFromUri(context, uri);
//            VMLog.d(TAG, "path:" + path);

            Music music = new Music();
            music.setMusicId(UUID.randomUUID().toString());
            music.setMusicName(title);
            music.setLoadState(Music.LoadState.LOADED);
            music.setPath(path);
            music.setAlbumName(album);
            music.setAuthor(artist);
            music.setSize(new File(path).length());
            return music;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Holder {
        private RCMusicControlEngine instance = new RCMusicControlEngine();
    }
}
