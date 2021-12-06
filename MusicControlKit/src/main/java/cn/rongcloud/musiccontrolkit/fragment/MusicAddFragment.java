package cn.rongcloud.musiccontrolkit.fragment;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.corekit.base.BaseFragment;
import cn.rongcloud.corekit.utils.SoftKeyboardUtils;
import cn.rongcloud.musiccontrolkit.MusicEngine;
import cn.rongcloud.musiccontrolkit.R;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.bean.MusicCategory;

/**
 * Created by hugo on 2021/11/24
 */
public class MusicAddFragment extends BaseFragment {

    private EditText etSearch;
    private ImageView ivClear;
    private ViewPager2 vpMusicList;
    private TabLayout tabCategory;
    private MusicCategoryAdapter adapter;
    private List<MusicCategory> categoryList = new ArrayList<>();
    private FrameLayout flSearch;
    private MusicAddListFragment searchFragment;

    public static MusicAddFragment getInstance() {
        return new MusicAddFragment();
    }

    @Override
    public int setLayoutId() {
        return R.layout.rckit_fragment_music_add;
    }

    @Override
    public void initView() {
        etSearch = (EditText) getLayout().findViewById(R.id.et_search);
        ivClear = (ImageView) getLayout().findViewById(R.id.iv_clear);
        vpMusicList = (ViewPager2) getLayout().findViewById(R.id.vp_music_list);
        tabCategory = (TabLayout) getLayout().findViewById(R.id.tab_category);
        flSearch = (FrameLayout) getLayout().findViewById(R.id.fl_search);

        adapter = new MusicCategoryAdapter(this);
        vpMusicList.setAdapter(adapter);
        vpMusicList.setOffscreenPageLimit(1);
        new TabLayoutMediator(tabCategory, vpMusicList, (tab, position) -> tab.setText(categoryList.get(position).getCategoryName())).attach();

        searchFragment = MusicAddListFragment.getSearchInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fl_search, searchFragment);
        transaction.commitAllowingStateLoss();

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Editable e = etSearch.getText();
                if (e == null || e.toString().isEmpty()) {
                    return true;
                }
                String keywords = e.toString().trim();
                if (keywords.isEmpty()) {
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    MusicEngine.getInstance().onSearchMusic(keywords, new DataCallback<List<Music>>() {
                        @Override
                        public void onResult(List<Music> music) {
                            showSearchResult(music);
                            SoftKeyboardUtils.hideSoftKeyboard(etSearch);
                        }
                    });
                }
                return true;
            }
        });
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    vpMusicList.setVisibility(View.INVISIBLE);
                    tabCategory.setVisibility(View.INVISIBLE);
                    flSearch.setVisibility(View.VISIBLE);
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    vpMusicList.setVisibility(View.VISIBLE);
                    tabCategory.setVisibility(View.VISIBLE);
                    flSearch.setVisibility(View.INVISIBLE);
                    ivClear.setVisibility(View.GONE);
                }
            }
        });
        ivClear.setOnClickListener(v -> {
            etSearch.setText("");
            etSearch.clearFocus();
            SoftKeyboardUtils.hideSoftKeyboard(etSearch);
            showSearchResult(null);
        });

        // 加载数据
        MusicEngine.getInstance().onLoadMusicCategory(new DataCallback<List<MusicCategory>>() {
            @Override
            public void onResult(List<MusicCategory> musicCategories) {
                setCategoryList(musicCategories);
            }
        });
    }

    private void showSearchResult(List<Music> music) {
        searchFragment.setMusicList(music);
    }

    private void setCategoryList(List<MusicCategory> categoryList) {
        if (categoryList != null) {
            this.categoryList.addAll(categoryList);
        }
        adapter.notifyDataSetChanged();
    }

    class MusicCategoryAdapter extends FragmentStateAdapter {

        public MusicCategoryAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return MusicAddListFragment.getInstance(categoryList.get(position).getCategoryId());
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }
    }

}
