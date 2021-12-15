package cn.rongcloud.kitdemo.musiccontrolkit;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.hfopen.sdk.entity.ChannelItem;
import com.hfopen.sdk.entity.ChannelSheet;
import com.hfopen.sdk.entity.HQListen;
import com.hfopen.sdk.entity.MusicList;
import com.hfopen.sdk.entity.MusicRecord;
import com.hfopen.sdk.entity.Record;
import com.hfopen.sdk.hInterface.DataResponse;
import com.hfopen.sdk.manager.HFOpenApi;
import com.hfopen.sdk.rx.BaseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.corekit.net.oklib.OkApi;
import cn.rongcloud.corekit.net.oklib.api.callback.FileIOCallBack;
import cn.rongcloud.corekit.utils.GsonUtil;
import cn.rongcloud.corekit.utils.HandlerUtils;
import cn.rongcloud.corekit.utils.ListUtil;
import cn.rongcloud.corekit.utils.VMLog;
import cn.rongcloud.kitdemo.MyApplication;
import cn.rongcloud.kitdemo.utils.ToastUtil;
import cn.rongcloud.musiccontrolkit.RCMusicControlEngine;
import cn.rongcloud.musiccontrolkit.bean.Effect;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.bean.MusicCategory;
import cn.rongcloud.musiccontrolkit.bean.MusicControl;
import cn.rongcloud.musiccontrolkit.iinterface.RCMusicKitListener;
import cn.rongcloud.rtc.api.IAudioEffectManager;
import cn.rongcloud.rtc.api.RCRTCAudioMixer;
import cn.rongcloud.rtc.api.RCRTCAudioMixer.MixingState;
import cn.rongcloud.rtc.api.RCRTCAudioMixer.MixingStateReason;
import cn.rongcloud.rtc.api.RCRTCEngine;
import cn.rongcloud.rtc.api.callback.RCRTCAudioMixingStateChangeListener;
import okhttp3.Response;

/**
 * Created by gyn on 2021/11/25
 * 音乐混音管理类，用户可以自己根据业务逻辑处理，
 * 这里是个例子
 */
public class MusicControlManager extends RCRTCAudioMixingStateChangeListener implements RCMusicKitListener {
    private static final String TAG = MusicControlManager.class.getSimpleName();
    private static MusicControlManager instance;
    private static MusicControl musicControl;
    // 缓存音乐文件的路径
    private String musicPath;

    public MusicControlManager() {
        // 这里存到了cache缓存目录下，可根据自己需求存到其他地方
        musicPath = MyApplication.app.getCacheDir().getAbsolutePath() + "/rckit_music_cache/";
        RCRTCAudioMixer.getInstance().setAudioMixingStateChangeListener(this);
        musicControl = MusicLocalCache.getInstance().getMusicControl();
    }

    public static MusicControlManager getInstance() {
        if (instance == null) {
            instance = new MusicControlManager();
        }
        return instance;
    }

    @Override
    public void onStateChanged(MixingState state, MixingStateReason reason) {
        if (state == MixingState.STOPPED) {
            //混音完成，可能的原因有：
            if (reason == MixingStateReason.ALL_LOOPS_COMPLETED) {
                //调用startMix方法时，传入的loopCount > 0，并且loopCount次数的混音已经完成

                // 这里自动混音下一首
                RCMusicControlEngine.getInstance().playNextMusic();
            } else if (reason == MixingStateReason.ONE_LOOP_COMPLETED) {
                //调用startMix方法时，传入的loopCount < 0（无限循环）或 > 1，混音完成一次。接下来会继续自动开始下一次混音。
            } else if (reason == MixingStateReason.STOPPED_BY_USER) {
                //调用stopMix方法停止混音
            }
        } else if (state == MixingState.PLAY) {
            //开始混音，可能的原因有：
            if (reason == MixingStateReason.STARTED_BY_USER) {
                //调用startMix方法开始混音
            } else if (reason == MixingStateReason.START_NEW_LOOP) {
                //调用startMix传入的loopCount < 0（无限循环）或 > 1时，自动开始下一次混音。
            } else if (reason == MixingStateReason.RESUMED_BY_USER) {
                //调用resume方法继续开始混音
            }
        } else if (state == MixingState.PAUSED) {
            //暂停混音，reason 为 MixingStateReason.PAUSED_BY_USER
        }
    }

    @Override
    public void onReportPlayingProgress(float progress) {

    }

    /**
     * 加载音乐列表页
     *
     * @param dataCallback 数据回传
     */
    @Override
    public void onLoadMusicList(DataCallback<List<Music>> dataCallback) {
        VMLog.d(TAG, "onLoadMusicList");
        // 先获取已在播放列表的歌曲，没有的话网络获取或本地获取，根据自己业务逻辑处理
        List<Music> oldMusicList = RCMusicControlEngine.getInstance().getMusicList();
        if (ListUtil.isNotEmpty(oldMusicList)) {
            dataCallback.onResult(oldMusicList);
        } else {
            // 从本地缓存获取，也可以是网络
            dataCallback.onResult(MusicLocalCache.getInstance().getMusicList());
        }
    }

    @Override
    public void onLoadMoreMusicList(DataCallback<List<Music>> dataCallback) {
        VMLog.d(TAG, "onLoadMoreMusicList");
    }

    /**
     * 加载音乐分类
     *
     * @param dataCallback 数据回传
     */
    @Override
    public void onLoadMusicCategory(DataCallback<List<MusicCategory>> dataCallback) {
        HFOpenApi.getInstance().channel(new DataResponse<ArrayList<ChannelItem>>() {
            @Override
            public void onError(@NonNull BaseException e) {
                VMLog.e(TAG, e.getCode() + ":" + e.getMsg());
            }

            @Override
            public void onSuccess(ArrayList<ChannelItem> channelItems, @NonNull String s) {
                VMLog.e(TAG, GsonUtil.obj2Json(channelItems));
                if (channelItems != null && channelItems.size() > 0) {
                    ChannelItem channelItem = channelItems.get(0);
                    HFOpenApi.getInstance().channelSheet(channelItem.getGroupId(), 0, 0, 1, 10, new DataResponse<ChannelSheet>() {
                        @Override
                        public void onError(@NonNull BaseException e) {

                        }

                        @Override
                        public void onSuccess(ChannelSheet channelSheet, @NonNull String s) {
                            if (channelSheet != null && channelSheet.getRecord() != null) {
                                List<MusicCategory> categoryList = new ArrayList<>();
                                MusicCategory category;
                                for (Record record : channelSheet.getRecord()) {
                                    category = new MusicCategory();
                                    category.setCategoryName(record.getSheetName());
                                    category.setCategoryId(record.getSheetId() + "");
                                    categoryList.add(category);
                                }
                                dataCallback.onResult(categoryList);
                            }
                        }
                    });
                }
            }
        });
        VMLog.d(TAG, "onLoadMusicCategory");
    }

    /**
     * 加载音乐分类列表
     *
     * @param category
     * @param dataCallback 数据回传
     */
    @Override
    public void onLoadMusicListByCategory(String category, DataCallback<List<Music>> dataCallback) {
        VMLog.d(TAG, "onLoadMusicListByCategory");
        HFOpenApi.getInstance().sheetMusic(Long.parseLong(category), 0, 1, 10, new DataResponse<MusicList>() {
            @Override
            public void onError(@NonNull BaseException e) {

            }

            @Override
            public void onSuccess(MusicList musicList, @NonNull String s) {
                if (musicList != null && ListUtil.isNotEmpty(musicList.getRecord())) {
                    VMLog.e(TAG, GsonUtil.obj2Json(musicList));
                    List<Music> myMusicList = new ArrayList<>();
                    for (MusicRecord record : musicList.getRecord()) {
                        myMusicList.add(convertToMusic(record));
                    }
                    dataCallback.onResult(myMusicList);
                }
            }
        });
    }

    @Override
    public void onLoadMoreMusicListByCategory(String category, DataCallback<List<Music>> dataCallback) {
        VMLog.d(TAG, "onLoadMoreMusicListByCategory");
    }

    /**
     * 搜索音乐
     *
     * @param dataCallback 数据回传
     */
    @Override
    public void onSearchMusic(String keywords, DataCallback<List<Music>> dataCallback) {
        VMLog.d(TAG, "onSearchMusic");
        HFOpenApi.getInstance().searchMusic(null, null, null, null, null, null, null, keywords, null, null, 0, 1, 100, new DataResponse<MusicList>() {
            @Override
            public void onError(@NonNull BaseException e) {

            }

            @Override
            public void onSuccess(MusicList musicList, @NonNull String s) {
                List<Music> myMusicList = new ArrayList<>();
                if (musicList != null && ListUtil.isNotEmpty(musicList.getRecord())) {
                    VMLog.e(TAG, GsonUtil.obj2Json(musicList));
                    for (MusicRecord record : musicList.getRecord()) {
                        myMusicList.add(convertToMusic(record));
                    }
                }
                dataCallback.onResult(myMusicList);
            }
        });
    }

    /**
     * 加载音乐控制页
     *
     * @param dataCallback 数据回传
     */
    @Override
    public void onLoadMusicControl(DataCallback<MusicControl> dataCallback) {
        dataCallback.onResult(musicControl);
        VMLog.d(TAG, "onLoadMusicControl");
    }

    @Override
    public void onLoadMusicDetail(Music music, DataCallback<Music> dataCallback) {
        VMLog.d(TAG, "onLoadMusicDetail");
        // 下载音乐详细信息，获取到音乐url
        HFOpenApi.getInstance().trafficHQListen(music.getMusicId(), "mp3", "320", new DataResponse<HQListen>() {
            @Override
            public void onError(@NonNull BaseException e) {

            }

            @Override
            public void onSuccess(HQListen hqListen, @NonNull String s) {
                music.setSize(hqListen.getFileSize());
                music.setFileUrl(hqListen.getFileUrl());
                dataCallback.onResult(music);
            }
        });
    }

    /**
     * 下载音乐到本地
     *
     * @param music
     * @param dataCallback
     */
    @Override
    public void onDownloadMusic(Music music, DataCallback<Music> dataCallback) {
        VMLog.d(TAG, "onDownloadMusic:" + GsonUtil.obj2Json(music));
        // 这里以音乐id命名，用户需根据自己业务需求下载音乐，这里就是简单的下载一下
        String name = music.getMusicId() + ".mp3";

        File file = new File(musicPath, name);
        // 本地已经下载过，就不下载了
        if (file.exists()) {
            musicLoadFinished(music, file.getAbsolutePath(), dataCallback);
            return;
        }

        OkApi.download(music.getFileUrl(), null, new FileIOCallBack(musicPath, name) {
            @Override
            public File onParse(Response response) throws IOException {
                return super.onParse(response);
            }

            @Override
            public File saveFile(Response response) throws IOException {
                File file = super.saveFile(response);
                VMLog.d(TAG, "download success ......" + Thread.currentThread());
                musicLoadFinished(music, file.getAbsolutePath(), dataCallback);
                return file;
            }
        });
    }

    /**
     * 本地选择音频文件后,解析uri后得到的Music
     *
     * @param music 返回music
     */
    @Override
    public void onSelectMusicFromLocal(Music music) {
        if (music == null) {
            return;
        }
        musicLoadFinished(music, music.getPath(), null);
    }

    /**
     * 音乐文件下载完之后的操作处理，用户可以根据需求自定义
     *
     * @param music
     * @param musicPath
     * @param dataCallback
     */
    private void musicLoadFinished(Music music, String musicPath, DataCallback<Music> dataCallback) {
        // 切换到主线程刷新UI
        HandlerUtils.mainThreadPost(new Runnable() {
            @Override
            public void run() {
                //更新UI

                // 下载完成后刷新当前列表
                music.setPath(musicPath);
                music.setLoadState(Music.LoadState.LOADED);
                if (dataCallback != null) {
                    dataCallback.onResult(music);
                }

                // 下载完成后根据自己业务逻辑处理，这里假设是播放列表为空，则添加到播放列表且自动播放，不为空则不播放
                boolean isEmpty = ListUtil.isEmpty(RCMusicControlEngine.getInstance().getMusicList());

                // 如果播放列表为空则自动播放
                if (isEmpty) {
                    RCMusicControlEngine.getInstance().playMusic(music);
                } else {
                    // 添加到播放列表
                    RCMusicControlEngine.getInstance().addMusic(music);
                }
                // 音乐列表缓存到本地
                saveMusicListToCache();
            }
        });
    }

    @Override
    public void onLoadEffectList(DataCallback<List<Effect>> dataCallback) {
        VMLog.d(TAG, "onLoadEffectList");
        List<Effect> effectList = MusicEffectManager.getInstance().getEffectList();
        dataCallback.onResult(effectList);
        // 预加载音效到融云 RCRTCEngine
        for (Effect effect : effectList) {
            RCRTCEngine.getInstance().getAudioEffectManager().preloadEffect(effect.getFilePath(), Integer.parseInt(effect.getSoundId()), new IAudioEffectManager.ILoadingStateCallback() {
                @Override
                public void complete(int error) {
                    if (error == -1) {
                        VMLog.e(TAG, "音效文件加载失败");
                    }
                }
            });
        }
    }

    /**
     * 置顶音乐
     *
     * @param fromMusic
     * @param downToMusic
     */
    @Override
    public void onTopMusic(Music fromMusic, Music downToMusic) {
        VMLog.d(TAG, "onTopMusic");
        saveMusicListToCache();
    }

    /**
     * 移除音乐
     *
     * @param music
     */
    @Override
    public void onDeleteMusic(Music music) {
        VMLog.d(TAG, "onDeleteMusic");
        saveMusicListToCache();
    }

    /**
     * 播放列表变化后缓存到本地
     */
    private void saveMusicListToCache() {
        VMLog.e(TAG, "saveMusicListToCache");
        MusicLocalCache.getInstance().saveMusicList(RCMusicControlEngine.getInstance().getMusicList());
    }

    private void saveMusicControlToCache() {
        VMLog.e(TAG, "saveMusicControlToCache");
        MusicLocalCache.getInstance().saveMusicControl(musicControl);
    }

    @Override
    public void onLocalVolumeChanged(int localVolume) {
        VMLog.d(TAG, "onLocalVolumeChanged");
        RCRTCAudioMixer.getInstance().setPlaybackVolume(localVolume);
        musicControl.setLocalVolume(localVolume);
        saveMusicControlToCache();
    }

    @Override
    public void onRemoteVolumeChanged(int remoteVolume) {
        VMLog.d(TAG, "onRemoteVolumeChanged");
        RCRTCAudioMixer.getInstance().setMixingVolume(remoteVolume);
        musicControl.setRemoteVolume(remoteVolume);
        saveMusicControlToCache();
    }

    @Override
    public void onMicVolumeChanged(int micVolume) {
        VMLog.d(TAG, "onMicVolumeChanged");
        RCRTCEngine.getInstance().getDefaultAudioStream().adjustRecordingVolume(micVolume);
        musicControl.setMicVolume(micVolume);
        saveMusicControlToCache();
    }

    @Override
    public void onEarsBackEnableChanged(boolean earsBackEnable) {
        VMLog.d(TAG, "onEarsBackEnableChanged");
        RCRTCEngine.getInstance().getDefaultAudioStream().enableEarMonitoring(earsBackEnable);
        musicControl.setEarsBackEnable(earsBackEnable);
        saveMusicControlToCache();
    }

    @Override
    public void onStartMixingWithMusic(Music music) {
        VMLog.d(TAG, "onStartMixingWithMusic:" + (music == null ? null : music.getMusicId()));
        VMLog.d(TAG, "onStartMixingWithMusic:" + GsonUtil.obj2Json(music));
        if (music == null || TextUtils.isEmpty(music.getPath()) || !new File(music.getPath()).exists()) {
            ToastUtil.show("本地音乐已删除，请重新添加");
            RCMusicControlEngine.getInstance().deleteMusic(music);
            return;
        }
        RCRTCAudioMixer.getInstance()
                .startMix(music.getPath(), RCRTCAudioMixer.Mode.MIX, true, 1);
        //快速测试播放下一首
//        RCRTCAudioMixer.getInstance().seekTo(0.9f);
    }

    @Override
    public void onResumeMixingWithMusic(Music music) {
        VMLog.d(TAG, "onResumeMixingWithMusic");
        RCRTCAudioMixer.getInstance().resume();
    }

    @Override
    public void onPauseMixingWithMusic(Music music) {
        VMLog.d(TAG, "onPauseMixingWithMusic");
        RCRTCAudioMixer.getInstance().pause();
    }

    @Override
    public void onPlayEffect(Effect effect) {
        VMLog.d(TAG, "onPlayEffect");
        RCRTCEngine.getInstance().getAudioEffectManager().stopAllEffects();
        RCRTCEngine.getInstance().getAudioEffectManager().playEffect(Integer.parseInt(effect.getSoundId()), 1, 50);
    }

    /**
     * 数据转换
     *
     * @param record
     * @return
     */
    private Music convertToMusic(MusicRecord record) {
        Music music = new Music();
        music.setMusicName(record.getMusicName());
        music.setMusicId(record.getMusicId());
        music.setAlbumName(record.getAlbumName());
        if (ListUtil.isNotEmpty(record.getCover())) {
            music.setCoverUrl(record.getCover().get(0).getUrl());
        }
        if (ListUtil.isNotEmpty(record.getArtist())) {
            music.setAuthor(record.getArtist().get(0).getName());
        } else if (ListUtil.isNotEmpty(record.getAuthor())) {
            music.setAuthor(record.getAuthor().get(0).getName());
        } else if (ListUtil.isNotEmpty(record.getComposer())) {
            music.setAuthor(record.getComposer().get(0).getName());
        } else if (ListUtil.isNotEmpty(record.getArranger())) {
            music.setAuthor(record.getArranger().get(0).getName());
        }
        return music;
    }
}
