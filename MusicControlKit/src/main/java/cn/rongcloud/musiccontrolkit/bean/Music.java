package cn.rongcloud.musiccontrolkit.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

import cn.rongcloud.corekit.utils.GsonUtil;

/**
 * Created by gyn on 2021/11/24
 */
public class Music implements Serializable {
    private static final String UPLOAD_ID = "UPLOAD_ID";
    private static final Music uploadItem = new Music();
    /**
     * 音乐服务器地址
     */
    @SerializedName("fileUrl")
    private String fileUrl;
    /**
     * 音乐图片
     */
    @SerializedName("coverUrl")
    private String coverUrl;
    /**
     * 音乐名称
     */
    @SerializedName("musicName")
    private String musicName;
    /**
     * 作者
     */
    @SerializedName("author")
    private String author;
    /**
     * 专辑
     */
    @SerializedName("albumName")
    private String albumName;
    /**
     * 本地文件路径
     */
    @SerializedName("path")
    private String path;
    /**
     * 文件大小（字节）
     */
    @SerializedName("size")
    private long size;
    /**
     * 下载状态
     */
    private LoadState loadState = LoadState.UN_LOAD;

    /**
     * 音乐 id
     */
    @SerializedName("musicId")
    private String musicId;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LoadState getLoadState() {
        return loadState;
    }

    public void setLoadState(LoadState loadState) {
        this.loadState = loadState;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    @Override
    public boolean equals(Object o) {
        return TextUtils.equals(GsonUtil.obj2Json(o), GsonUtil.obj2Json(this));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileUrl, coverUrl, musicName, author, albumName, path, size, loadState, musicId);
    }

    public boolean isUnLoad() {
        return loadState == LoadState.UN_LOAD;
    }

    public boolean isLoaded() {
        return loadState == LoadState.LOADED;
    }

    public boolean isLoading() {
        return loadState == LoadState.LOADING;
    }

    public static Music getUploadMusic() {
        uploadItem.setMusicId(UPLOAD_ID);
        uploadItem.setMusicName("本地上传");
        uploadItem.setLoadState(LoadState.UN_LOAD);
        return uploadItem;
    }

    public enum LoadState {
        /**
         * 正在下载
         */
        LOADING,
        /**
         * 已经下载
         */
        LOADED,
        /**
         * 未下载
         */
        UN_LOAD
    }

    public boolean isUploadMusicItem() {
        return TextUtils.equals(musicId, UPLOAD_ID);
    }
}
