package cn.rongcloud.musiccontrolkit.iinterface;

import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.musiccontrolkit.bean.Effect;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.bean.MusicCategory;
import cn.rongcloud.musiccontrolkit.bean.MusicControl;

/**
 * Created by gyn on 2021/12/1
 */
public abstract class AbsMusicEngine implements RCMusicKitListener {
    private RCMusicKitListener rcMusicKitListener;

    public void setListener(RCMusicKitListener rcMusicKitListener) {
        this.rcMusicKitListener = rcMusicKitListener;
    }

    @Override
    public void onLoadMusicList(DataCallback<List<Music>> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onLoadMusicList(dataCallback);
    }

    @Override
    public void onLoadMoreMusicList(DataCallback<List<Music>> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onLoadMoreMusicList(dataCallback);
    }

    @Override
    public void onLoadMusicCategory(DataCallback<List<MusicCategory>> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onLoadMusicCategory(dataCallback);
    }

    @Override
    public void onLoadMusicListByCategory(String category, DataCallback<List<Music>> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onLoadMusicListByCategory(category, dataCallback);
    }

    @Override
    public void onLoadMoreMusicListByCategory(String category, DataCallback<List<Music>> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onLoadMoreMusicListByCategory(category, dataCallback);
    }

    @Override
    public void onSearchMusic(String keywords, DataCallback<List<Music>> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onSearchMusic(keywords, dataCallback);
    }

    @Override
    public void onLoadMusicControl(DataCallback<MusicControl> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onLoadMusicControl(dataCallback);
    }

    @Override
    public void onLoadMusicDetail(Music music, DataCallback<Music> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onLoadMusicDetail(music, dataCallback);
    }

    @Override
    public void onLoadEffectList(DataCallback<List<Effect>> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onLoadEffectList(dataCallback);
    }

    @Override
    public void onTopMusic(Music fromMusic, Music downToMusic) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onTopMusic(fromMusic, downToMusic);
    }

    @Override
    public void onDeleteMusic(Music music) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onDeleteMusic(music);
    }

    @Override
    public void onDownloadMusic(Music music, DataCallback<Music> dataCallback) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onDownloadMusic(music, dataCallback);
    }

    @Override
    public void onSelectMusicFromLocal(Music music) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onSelectMusicFromLocal(music);
    }

    @Override
    public void onLocalVolumeChanged(int localVolume) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onLocalVolumeChanged(localVolume);
    }

    @Override
    public void onRemoteVolumeChanged(int remoteVolume) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onRemoteVolumeChanged(remoteVolume);
    }

    @Override
    public void onMicVolumeChanged(int micVolume) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onMicVolumeChanged(micVolume);
    }

    @Override
    public void onEarsBackEnableChanged(boolean earsBackEnable) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onEarsBackEnableChanged(earsBackEnable);
    }

    @Override
    public void onStartMixingWithMusic(Music music) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onStartMixingWithMusic(music);
    }

    @Override
    public void onResumeMixingWithMusic(Music music) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onResumeMixingWithMusic(music);
    }

    @Override
    public void onPauseMixingWithMusic(Music music) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onPauseMixingWithMusic(music);
    }

    @Override
    public void onStopMixingWithMusic() {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onStopMixingWithMusic();
    }

    @Override
    public void onPlayEffect(Effect effect) {
        if (rcMusicKitListener == null) {
            return;
        }
        rcMusicKitListener.onPlayEffect(effect);
    }

    public void release() {
        rcMusicKitListener = null;
    }
}
