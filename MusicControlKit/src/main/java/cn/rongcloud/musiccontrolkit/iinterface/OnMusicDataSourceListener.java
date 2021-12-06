package cn.rongcloud.musiccontrolkit.iinterface;

import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.musiccontrolkit.bean.Effect;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.bean.MusicCategory;
import cn.rongcloud.musiccontrolkit.bean.MusicControl;

public interface OnMusicDataSourceListener {
    /**
     * 加载音乐列表
     * 第一次加载或者刷新
     *
     * @param dataCallback 数据回调
     */
    void onLoadMusicList(DataCallback<List<Music>> dataCallback);

    /**
     * 加载音乐列表
     * 加载更多
     *
     * @param dataCallback 数据回调
     */
    void onLoadMoreMusicList(DataCallback<List<Music>> dataCallback);

    /**
     * 加载音乐分类
     *
     * @param dataCallback 数据回调
     */
    void onLoadMusicCategory(DataCallback<List<MusicCategory>> dataCallback);

    /**
     * 根据分类加载音乐列表
     * 第一次加载或者刷新加载
     *
     * @param category     分类
     * @param dataCallback 数据回调
     */
    void onLoadMusicListByCategory(String category, DataCallback<List<Music>> dataCallback);

    /**
     * 根据分类加载音乐列表
     * 加载更多
     *
     * @param category     分类
     * @param dataCallback 数据回调
     */
    void onLoadMoreMusicListByCategory(String category, DataCallback<List<Music>> dataCallback);

    /**
     * 搜索音乐
     *
     * @param keywords     关键词
     * @param dataCallback 数据回调
     */
    void onSearchMusic(String keywords, DataCallback<List<Music>> dataCallback);

    /**
     * 加载音乐控制数据
     *
     * @param dataCallback 数据回调
     */
    void onLoadMusicControl(DataCallback<MusicControl> dataCallback);

    /**
     * 下载音乐信息，某些情况Music列表中的数据没有 fileUrl 数据，
     * 此种情况需要根据音乐 id 从新拉取音乐信息获取 fileUrl，用来下载音乐到本地
     *
     * @param music        要下载的音乐
     * @param dataCallback 数据回调
     */
    void onLoadMusicDetail(Music music, DataCallback<Music> dataCallback);

    /**
     * 根据url下载音乐原始资源
     *
     * @param music
     * @param dataCallback
     */
    void onDownloadMusic(Music music, DataCallback<Music> dataCallback);

    /**
     * 加载气氛列表
     *
     * @param dataCallback 数据回调
     */
    void onLoadEffectList(DataCallback<List<Effect>> dataCallback);

}