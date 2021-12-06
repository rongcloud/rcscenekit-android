package cn.rongcloud.musiccontrolkit.iinterface;

import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.musiccontrolkit.bean.Effect;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.bean.MusicCategory;
import cn.rongcloud.musiccontrolkit.bean.MusicControl;

/**
 * Created by hugo on 2021/12/1
 */
public abstract class AbsMusicEngine implements OnMusicDataSourceListener, OnMusicPlayerListener, OnMusicOperateListener {
    private OnMusicDataSourceListener onMusicDataSourceListener;
    private OnMusicOperateListener onMusicOperateListener;
    private OnMusicPlayerListener onMusicPlayerListener;

    public void setListener(OnMusicDataSourceListener onMusicDataSourceListener, OnMusicOperateListener onMusicOperateListener, OnMusicPlayerListener onMusicPlayerListener) {
        this.onMusicDataSourceListener = onMusicDataSourceListener;
        this.onMusicOperateListener = onMusicOperateListener;
        this.onMusicPlayerListener = onMusicPlayerListener;
    }

    @Override
    public void onLoadMusicList(DataCallback<List<Music>> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onLoadMusicList(dataCallback);
    }

    @Override
    public void onLoadMoreMusicList(DataCallback<List<Music>> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onLoadMoreMusicList(dataCallback);
    }

    @Override
    public void onLoadMusicCategory(DataCallback<List<MusicCategory>> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onLoadMusicCategory(dataCallback);
    }

    @Override
    public void onLoadMusicListByCategory(String category, DataCallback<List<Music>> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onLoadMusicListByCategory(category, dataCallback);
    }

    @Override
    public void onLoadMoreMusicListByCategory(String category, DataCallback<List<Music>> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onLoadMoreMusicListByCategory(category, dataCallback);
    }

    @Override
    public void onSearchMusic(String keywords, DataCallback<List<Music>> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onSearchMusic(keywords, dataCallback);
    }

    @Override
    public void onLoadMusicControl(DataCallback<MusicControl> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onLoadMusicControl(dataCallback);
    }

    @Override
    public void onLoadMusicDetail(Music music, DataCallback<Music> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onLoadMusicDetail(music, dataCallback);
    }

    @Override
    public void onLoadEffectList(DataCallback<List<Effect>> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onLoadEffectList(dataCallback);
    }

    @Override
    public void onTopMusic(Music fromMusic, Music downToMusic) {
        if (onMusicOperateListener == null) {
            return;
        }
        onMusicOperateListener.onTopMusic(fromMusic, downToMusic);
    }

    @Override
    public void onDeleteMusic(Music music) {
        if (onMusicOperateListener == null) {
            return;
        }
        onMusicOperateListener.onDeleteMusic(music);
    }

    @Override
    public void onDownloadMusic(Music music, DataCallback<Music> dataCallback) {
        if (onMusicDataSourceListener == null) {
            return;
        }
        onMusicDataSourceListener.onDownloadMusic(music, dataCallback);
    }

    @Override
    public void onLocalVolumeChanged(int localVolume) {
        if (onMusicPlayerListener == null) {
            return;
        }
        onMusicPlayerListener.onLocalVolumeChanged(localVolume);
    }

    @Override
    public void onRemoteVolumeChanged(int remoteVolume) {
        if (onMusicPlayerListener == null) {
            return;
        }
        onMusicPlayerListener.onRemoteVolumeChanged(remoteVolume);
    }

    @Override
    public void onMicVolumeChanged(int micVolume) {
        if (onMusicPlayerListener == null) {
            return;
        }
        onMusicPlayerListener.onMicVolumeChanged(micVolume);
    }

    @Override
    public void onEarsBackEnableChanged(boolean earsBackEnable) {
        if (onMusicPlayerListener == null) {
            return;
        }
        onMusicPlayerListener.onEarsBackEnableChanged(earsBackEnable);
    }

    @Override
    public void onStartMixingWithMusic(Music music) {
        if (onMusicPlayerListener == null) {
            return;
        }
        onMusicPlayerListener.onStartMixingWithMusic(music);
    }

    @Override
    public void onResumeMixingWithMusic(Music music) {
        if (onMusicPlayerListener == null) {
            return;
        }
        onMusicPlayerListener.onResumeMixingWithMusic(music);
    }

    @Override
    public void onPauseMixingWithMusic(Music music) {
        if (onMusicPlayerListener == null) {
            return;
        }
        onMusicPlayerListener.onPauseMixingWithMusic(music);
    }

    @Override
    public void onPlayEffect(Effect effect) {
        if (onMusicPlayerListener == null) {
            return;
        }
        onMusicPlayerListener.onPlayEffect(effect);
    }

    public void release() {
        onMusicPlayerListener = null;
        onMusicDataSourceListener = null;
        onMusicOperateListener = null;
    }
}
