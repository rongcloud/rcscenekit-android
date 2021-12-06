package cn.rongcloud.musiccontrolkit.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.corekit.base.BaseFragment;
import cn.rongcloud.corekit.utils.GlideUtil;
import cn.rongcloud.corekit.utils.ListUtil;
import cn.rongcloud.musiccontrolkit.MusicControlKitInit;
import cn.rongcloud.musiccontrolkit.MusicEngine;
import cn.rongcloud.musiccontrolkit.R;
import cn.rongcloud.musiccontrolkit.bean.Music;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hugo on 2021/11/24
 */
public class MusicAddListFragment extends BaseFragment {
    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String IS_SEARCH = "IS_SEARCH";
    private RecyclerView rvMusicList;
    private ImageView emptyImageView;
    private MusicListAdapter adapter;
    private String categoryId;
    private boolean isSearch;
    private List<Music> musicList = new ArrayList<>();
    private SmartRefreshLayout srlRefresh;


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
    public void initView() {
        categoryId = getArguments().getString(CATEGORY_ID, "");
        isSearch = getArguments().getBoolean(IS_SEARCH, false);
        rvMusicList = getLayout().findViewById(R.id.rv_music_list);
        srlRefresh = getLayout().findViewById(R.id.srl_refresh);
        emptyImageView = getLayout().findViewById(R.id.iv_empty);
        adapter = new MusicListAdapter();
        rvMusicList.setAdapter(adapter);
        if (isSearch) {
            srlRefresh.setEnableLoadMore(false);
            srlRefresh.setEnableRefresh(false);
        } else {
            boolean enableRefreshAndLoadMore = MusicControlKitInit.getInstance().isEnableRefreshAndLoadMore();
            srlRefresh.setEnableLoadMore(enableRefreshAndLoadMore);
            srlRefresh.setEnableRefresh(enableRefreshAndLoadMore);

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
            refreshData();
        }

        listenerMusicList();
    }

    private void listenerMusicList() {
        MusicEngine.getInstance().musicIdListObserve().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                checkIsInMusicList(musicList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void refreshData() {
        MusicEngine.getInstance().onLoadMusicListByCategory(categoryId, new DataCallback<List<Music>>() {
            @Override
            public void onResult(List<Music> musicList) {
                setMusicList(musicList);
            }
        });
    }

    private void loadMoreData() {
        MusicEngine.getInstance().onLoadMoreMusicListByCategory(categoryId, new DataCallback<List<Music>>() {
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
        adapter.notifyDataSetChanged();
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadMore();
    }

    private List<Music> checkIsInMusicList(List<Music> musicList) {
        if (musicList != null) {
            for (Music music : musicList) {
                if (MusicEngine.getInstance().isInMusicList(music.getMusicId())) {
                    music.setLoadState(Music.LoadState.LOADED);
                } else if (music.isLoaded()) {
                    music.setLoadState(Music.LoadState.UN_LOAD);
                }
            }
        }
        return musicList;
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

        public MusicItemViewHolder(@NonNull View itemView) {
            super(itemView);
            civTheme = (CircleImageView) itemView.findViewById(R.id.civ_theme);
            tvMusicName = (TextView) itemView.findViewById(R.id.tv_music_name);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvSize = (TextView) itemView.findViewById(R.id.tv_size);
            ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
            pbLoad = itemView.findViewById(R.id.pb_load);
        }

        private void initView(Music music) {
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
                    MusicEngine.getInstance().onLoadMusicDetail(music, new DataCallback<Music>() {
                        @Override
                        public void onResult(Music m) {
                            if (m == null) {
                                music.setLoadState(Music.LoadState.UN_LOAD);
                                adapter.notifyDataSetChanged();
                                return;
                            }
                            music.setFileUrl(m.getFileUrl());
                            if (!TextUtils.isEmpty(music.getFileUrl())) {
                                MusicEngine.getInstance().onDownloadMusic(music, new DataCallback<Music>() {
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
