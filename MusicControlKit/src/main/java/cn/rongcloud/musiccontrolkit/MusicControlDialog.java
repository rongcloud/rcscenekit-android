package cn.rongcloud.musiccontrolkit;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.base.BaseBottomSheetDialog;
import cn.rongcloud.corekit.widget.RealtimeBlurView;
import cn.rongcloud.musiccontrolkit.fragment.MusicAddFragment;
import cn.rongcloud.musiccontrolkit.fragment.MusicControlFragment;
import cn.rongcloud.musiccontrolkit.fragment.MusicListFragment;
import cn.rongcloud.musiccontrolkit.iinterface.OnMusicDataSourceListener;
import cn.rongcloud.musiccontrolkit.iinterface.OnMusicOperateListener;
import cn.rongcloud.musiccontrolkit.iinterface.OnMusicPlayerListener;
import cn.rongcloud.musiccontrolkit.iinterface.OnSwitchPageListener;
import cn.rongcloud.musiccontrolkit.widget.EffectSnackBar;

/**
 * Created by hugo on 2021/11/23
 */
public class MusicControlDialog extends BaseBottomSheetDialog implements OnSwitchPageListener {

    private RealtimeBlurView rbvTop;
    private ConstraintLayout clTop;
    private TabLayout tlTab;
    private RealtimeBlurView rbvBottom;
    private ViewPager2 vpMusic;
    private ImageView ivEffect;
    private View space;
    private List<Fragment> fragmentList = new ArrayList<>();
    private TabLayoutMediator tabLayoutMediator;
    private EffectSnackBar effectSnackBar;

    public MusicControlDialog(OnMusicDataSourceListener onMusicDataSourceListener, OnMusicOperateListener onMusicOperateListener, OnMusicPlayerListener onMusicPlayerListener) {
        super(R.layout.rckit_dialog_music_control);
        MusicEngine.getInstance().setListener(onMusicDataSourceListener, onMusicOperateListener, onMusicPlayerListener);
    }

    @Override
    protected void initView() {
        rbvTop = (RealtimeBlurView) getView().findViewById(R.id.rbv_top);
        clTop = (ConstraintLayout) getView().findViewById(R.id.cl_top);
        tlTab = (TabLayout) getView().findViewById(R.id.tl_tab);
        rbvBottom = (RealtimeBlurView) getView().findViewById(R.id.rbv_bottom);
        vpMusic = (ViewPager2) getView().findViewById(R.id.vp_music);
        ivEffect = (ImageView) getView().findViewById(R.id.iv_effect);
        space = getView().findViewById(R.id.spacer);

        fragmentList.add(MusicListFragment.getInstance(this));
        fragmentList.add(MusicAddFragment.getInstance());
        fragmentList.add(MusicControlFragment.getInstance());
        MusicFragmentAdapter adapter = new MusicFragmentAdapter(this);
        vpMusic.setAdapter(adapter);
        vpMusic.setUserInputEnabled(false);

        int[] icons = new int[]{R.drawable.rckit_tab_music_list_selector, R.drawable.rckit_tab_music_add_selector, R.drawable.rckit_tab_music_control_selector};
        ImageView imageView;
        List<ImageView> iconList = new ArrayList<>();
        for (int icon : icons) {
            imageView = new ImageView(getContext());
            imageView.setImageResource(icon);
            iconList.add(imageView);
        }
        if (tabLayoutMediator == null) {
            tabLayoutMediator = new TabLayoutMediator(tlTab, vpMusic, (tab, position) -> tab.setCustomView(iconList.get(position)));
            tabLayoutMediator.attach();
        }

        ivEffect.setOnClickListener(v -> {
            if (effectSnackBar == null) {
                effectSnackBar = EffectSnackBar.make((ViewGroup) getView());
            }
            if (effectSnackBar.isShown()) {
                ivEffect.setSelected(false);
                effectSnackBar.dismiss();
            } else {
                ivEffect.setSelected(true);
                effectSnackBar.show();
            }
        });
        space.setOnClickListener(v -> {
            dismiss();
        });
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    public void onSwitch(int position) {
        if (position < fragmentList.size()) {
            vpMusic.setCurrentItem(position);
        }
    }

    class MusicFragmentAdapter extends FragmentStateAdapter {

        public MusicFragmentAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }


}
