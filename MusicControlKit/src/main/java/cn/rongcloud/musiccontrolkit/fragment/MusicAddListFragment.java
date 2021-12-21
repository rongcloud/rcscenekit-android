package cn.rongcloud.musiccontrolkit.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.corekit.base.RCFragment;
import cn.rongcloud.corekit.utils.GlideUtil;
import cn.rongcloud.corekit.utils.ListUtil;
import cn.rongcloud.corekit.utils.UiUtils;
import cn.rongcloud.musiccontrolkit.R;
import cn.rongcloud.musiccontrolkit.RCMusicControlEngine;
import cn.rongcloud.musiccontrolkit.RCMusicControlKit;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.bean.MusicAddConfig;
import cn.rongcloud.musiccontrolkit.bean.MusicControlKitConfig;
import cn.rongcloud.musiccontrolkit.bean.MusicItemConfig;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gyn on 2021/11/24
 */
public class MusicAddListFragment extends RCFragment<MusicControlKitConfig> {
    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String IS_SEARCH = "IS_SEARCH";
    private final ActivityResultLauncher mLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result != null
                                && result.getData() != null
                                && result.getData().getData() != null) {
                            Uri uri = result.getData().getData();
                            Music music = RCMusicControlEngine.getInstance().parseLocalMusic(uri);
                            RCMusicControlEngine.getInstance().onSelectMusicFromLocal(music);
                        }
                    });
    private RecyclerView rvMusicList;
    private ImageView emptyImageView;
    private MusicListAdapter adapter;
    private String categoryId;
    private boolean isSearch;
    private List<Music> musicList = new ArrayList<>();
    private SmartRefreshLayout srlRefresh;
    private final ActivityResultLauncher permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                boolean allOk = true;
                for (String item : result.keySet()) {
                    try {
                        boolean bOK = result.get(item);
                        if (!bOK) {
                            allOk = false;
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (allOk) {
                    launchFilePick();
                }
            });
    private ConstraintLayout clRoot;
    private MusicAddConfig musicAddConfig;
    private MusicItemConfig itemConfig;

    public static MusicAddListFragment getInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_ID, categoryId);
        MusicAddListFragment fragment = new MusicAddListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MusicAddListFragment getSearchInstance() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_SEARCH, true);
        MusicAddListFragment fragment = new MusicAddListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.rckit_fragment_music_add_list;
    }

    @Override
    public MusicControlKitConfig getKitConfig() {
        return RCMusicControlKit.getInstance().getKitConfig();
    }

    @Override
    public void initView() {
        categoryId = getArguments().getString(CATEGORY_ID, "");
        isSearch = getArguments().getBoolean(IS_SEARCH, false);
        clRoot = getLayout().findViewById(R.id.cl_root);
        rvMusicList = getLayout().findViewById(R.id.rv_music_list);
        srlRefresh = getLayout().findViewById(R.id.srl_refresh);
        emptyImageView = getLayout().findViewById(R.id.iv_empty);
        adapter = new MusicListAdapter();
        rvMusicList.setAdapter(adapter);
        if (isSearch) {
            srlRefresh.setEnableLoadMore(false);
            srlRefresh.setEnableRefresh(false);
        } else {
            srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    loadMoreData();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshData();
                }
            });
        }
    }

    @Override
    public void initConfig(MusicControlKitConfig musicControlKitConfig) {
        musicAddConfig = musicControlKitConfig.getMusicAdd();
        if (musicAddConfig != null) {
            if (isSearch) {
                srlRefresh.setEnableLoadMore(false);
                srlRefresh.setEnableRefresh(false);
            } else {
                srlRefresh.setEnableLoadMore(musicAddConfig.isLoadMoreEnable());
                srlRefresh.setEnableRefresh(musicAddConfig.isRefreshEnable());
            }
            clRoot.setBackgroundColor(musicAddConfig.getBackgroundColor().getColor());
            itemConfig = musicAddConfig.getMusicItem();
        }
        if (!isSearch) {
            refreshData();
        }
        listenerMusicList();
    }

    private void listenerMusicList() {
        RCMusicControlEngine.getInstance().musicIdListObserve().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                checkIsInMusicList(musicList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void refreshData() {
        RCMusicControlEngine.getInstance().onLoadMusicListByCategory(categoryId, new DataCallback<List<Music>>() {
            @Override
            public void onResult(List<Music> musicList) {
                setMusicList(musicList);
            }
        });
    }

    private void loadMoreData() {
        RCMusicControlEngine.getInstance().onLoadMoreMusicListByCategory(categoryId, new DataCallback<List<Music>>() {
            @Override
            public void onResult(List<Music> musicList) {
                addMusicList(musicList);
            }
        });
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList.clear();
        if (ListUtil.isEmpty(musicList)) {
            emptyImageView.setVisibility(View.VISIBLE);
        } else {
            emptyImageView.setVisibility(View.GONE);
        }
        addMusicList(musicList);
    }

    private void addMusicList(List<Music> musicList) {
        if (musicList != null) {
            this.musicList.addAll(checkIsInMusicList(musicList));
        }
        if (musicAddConfig != null && musicAddConfig.isUploadMusicEnable() && !isSearch) {
            Music music = Music.getUploadMusic();
            this.musicList.remove(music);
            this.musicList.add(music);
        }
        adapter.notifyDataSetChanged();
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadMore();
    }

    private List<Music> checkIsInMusicList(List<Music> musicList) {
        if (musicList != null) {
            for (Music music : musicList) {
                if (RCMusicControlEngine.getInstance().isInMusicList(music.getMusicId())) {
                    music.setLoadState(Music.LoadState.LOADED);
                } else if (music.isLoaded()) {
                    music.setLoadState(Music.LoadState.UN_LOAD);
                }
            }
        }
        return musicList;
    }

    private void launchFilePick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new ArrayList<String>().addAll(
                Arrays.asList("audio/x-mpeg",
                        "audio/aac",
                        "audio/mp4a-latm",
                        "audio/x-wav",
                        "audio/ogg",
                        "audio/3gpp")));
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (mLauncher != null) {
            mLauncher.launch(intent);
        }
    }

    private void requestStoragePermission() {
        permissionLauncher.launch(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    class MusicListAdapter extends RecyclerView.Adapter<MusicItemViewHolder> {

        @NonNull
        @Override
        public MusicItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MusicItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.rckit_item_music_add, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MusicItemViewHolder holder, int position) {
            holder.initView(musicList.get(position));
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }

    class MusicItemViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civTheme;
        private TextView tvMusicName;
        private TextView tvAuthor;
        private TextView tvSize;
        private ImageView ivAdd;
        private ProgressBar pbLoad;
        private View separator;
        private Guideline glGuideStart;
        private Guideline glGuideEnd;

        public MusicItemViewHolder(@NonNull View itemView) {
            super(itemView);
            civTheme = (CircleImageView) itemView.findViewById(R.id.civ_theme);
            tvMusicName = (TextView) itemView.findViewById(R.id.tv_music_name);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvSize = (TextView) itemView.findViewById(R.id.tv_size);
            ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
            pbLoad = itemView.findViewById(R.id.pb_load);
            separator = itemView.findViewById(R.id.v_separator);
            glGuideStart = itemView.findViewById(R.id.gl_guide_start);
            glGuideEnd = itemView.findViewById(R.id.gl_guide_end);
            initConfig();
        }

        private void initConfig() {
            if (itemConfig != null) {
                UiUtils.setViewSize(itemView, itemConfig.getSize());

                glGuideStart.setGuidelineBegin(itemConfig.getContentInsets().getLeftPx());
                glGuideEnd.setGuidelineEnd(itemConfig.getContentInsets().getRightPx());

                UiUtils.setViewSize(civTheme, itemConfig.getCoverSize());
                UiUtils.setTextAttributes(tvMusicName, itemConfig.getTitleAttributes());
                UiUtils.setTextAttributes(tvAuthor, itemConfig.getContentAttributes());
                UiUtils.setTextAttributes(tvSize, itemConfig.getSizeAttributes());
                if (itemConfig.getSeparatorAttributes() != null) {
                    UiUtils.setViewSize(separator, itemConfig.getSeparatorAttributes().getSize());
                    separator.setBackgroundColor(itemConfig.getSeparatorAttributes().getBackground().getColor());
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) separator.getLayoutParams();
                    params.leftMargin = itemConfig.getSeparatorAttributes().getInsets().getLeftPx();
                    params.rightMargin = itemConfig.getSeparatorAttributes().getInsets().getRightPx();
                    separator.setLayoutParams(params);
                }
                UiUtils.setImageAttribute(ivAdd, itemConfig.getAddIconAttributes(), R.drawable.rckit_ic_music_add_selector, RCMusicControlKit.getInstance().getAssetsPath());
                UiUtils.setViewSize(pbLoad, itemConfig.getAddIconAttributes().getSize());
                UiUtils.setPadding(pbLoad, itemConfig.getAddIconAttributes().getInsets());
                pbLoad.setIndeterminateTintList(ColorStateList.valueOf(itemConfig.getHighlightColor().getColor()));
            }
        }

        private void initView(Music music) {
            if (music.isUploadMusicItem()) {
                civTheme.setImageResource(R.drawable.rckit_ic_music_upload);
                tvMusicName.setText(music.getMusicName());
                tvAuthor.setVisibility(View.GONE);
                tvSize.setVisibility(View.GONE);
                pbLoad.setVisibility(View.INVISIBLE);
                ivAdd.setVisibility(View.VISIBLE);
                ivAdd.setSelected(false);
                ivAdd.setOnClickListener(v -> {
                    requestStoragePermission();
                });
                return;
            }
            tvAuthor.setVisibility(View.VISIBLE);
            tvSize.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(civTheme, music.getCoverUrl(), R.drawable.rckit_ic_music_cover);
            tvMusicName.setText(music.getMusicName());
            tvAuthor.setText(music.getAuthor());
            tvSize.setText(music.getAlbumName());
            if (music.isLoading()) {
                pbLoad.setVisibility(View.VISIBLE);
                ivAdd.setVisibility(View.INVISIBLE);
            } else {
                pbLoad.setVisibility(View.INVISIBLE);
                ivAdd.setVisibility(View.VISIBLE);
                ivAdd.setSelected(music.isLoaded());
            }
            ivAdd.setOnClickListener(v -> {
                if (music.isUnLoad()) {
                    music.setLoadState(Music.LoadState.LOADING);
                    adapter.notifyDataSetChanged();
                    RCMusicControlEngine.getInstance().onLoadMusicDetail(music, new DataCallback<Music>() {
                        @Override
                        public void onResult(Music m) {
                            if (m == null) {
                                music.setLoadState(Music.LoadState.UN_LOAD);
                                adapter.notifyDataSetChanged();
                                return;
                            }
                            music.setFileUrl(m.getFileUrl());
                            if (!TextUtils.isEmpty(music.getFileUrl())) {
                                RCMusicControlEngine.getInstance().onDownloadMusic(music, new DataCallback<Music>() {
                                    @Override
                                    public void onResult(Music m) {
                                        if (m == null) {
                                            music.setLoadState(Music.LoadState.UN_LOAD);
                                            adapter.notifyDataSetChanged();
                                            return;
                                        }
                                        music.setLoadState(m.getLoadState());
                                        music.setPath(m.getPath());
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            } else {
                                music.setLoadState(Music.LoadState.UN_LOAD);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            });
        }
    }
}
