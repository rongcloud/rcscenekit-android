package cn.rongcloud.musiccontrolkit.iinterface;

import cn.rongcloud.musiccontrolkit.bean.Music;

public interface OnMusicOperateListener {
    /**
     * 这里置顶的逻辑是：把要操作的歌曲移到正在播放歌曲的下面，
     * 如果当前没有正在播放的歌曲则播放该歌曲，位置不变化
     *
     * @param fromMusic   要操作的music
     * @param downToMusic 当前正在播放的音乐
     */
    void onTopMusic(Music fromMusic, Music downToMusic);

    /**
     * @param music 要删除音乐
     */
    void onDeleteMusic(Music music);
}