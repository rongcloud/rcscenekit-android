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

import cn.rongcloud.corekit.base.RCBottomSheetDialog;
import cn.rongcloud.corekit.bean.RCAttributes;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCSize;
import cn.rongcloud.corekit.utils.UiUtils;
import cn.rongcloud.corekit.widget.RealtimeBlurView;
import cn.rongcloud.musiccontrolkit.bean.MusicControlKitConfig;
import cn.rongcloud.musiccontrolkit.bean.MusicToolbarConfig;
import cn.rongcloud.musiccontrolkit.bean.SoundEffectConfig;
import cn.rongcloud.musiccontrolkit.fragment.MusicAddFragment;
import cn.rongcloud.musiccontrolkit.fragment.MusicControlFragment;
import cn.rongcloud.musiccontrolkit.fragment.MusicListFragment;
import cn.rongcloud.musiccontrolkit.iinterface.OnSwitchPageListener;
import cn.rongcloud.musiccontrolkit.widget.EffectSnackBar;

/**
 * Created by gyn on 2021/11/23
 */
public class MusicControlDialog extends RCBottomSheetDialog<MusicControlKitConfig> implements OnSwitchPageListener {
    public static final String TAG = MusicControlDialog.class.getSimpleName();
    private RealtimeBlurView rbvTop;
    private ConstraintLayout clTop;
    private TabLayout tlTab;
    private RealtimeBlurView rbvBottom;
    private ViewPager2 vpMusic;
    private ImageView ivEffect;
    private View space;
    private final List<Fragment> fragmentList = new ArrayList<>();
    private TabLayoutMediator tabLayoutMediator;
    private EffectSnackBar effectSnackBar;

    @Override
    public int setLayoutId() {
        return R.layout.rckit_dialog_music_control;
    }

    @Override
    public MusicControlKitConfig getKitConfig() {
        return RCMusicControlKit.getInstance().getKitConfig();
    }

    @Override
    public void initView() {
        rbvTop = (RealtimeBlurView) getView().findViewById(R.id.rbv_top);
        clTop = (ConstraintLayout) getView().findViewById(R.id.cl_top);
        tlTab = (TabLayout) getView().findViewById(R.id.tl_tab);
        rbvBottom = (RealtimeBlurView) getView().findViewById(R.id.rbv_bottom);
        vpMusic = (ViewPager2) getView().findViewById(R.id.vp_music);
        ivEffect = (ImageView) getView().findViewById(R.id.iv_effect);
        space = getView().findViewById(R.id.spacer);
        space.setOnClickListener(v -> {
            dismiss();
        });
    }

    @Override
    public void initConfig(MusicControlKitConfig musicControlKitConfig) {
        MusicToolbarConfig toolbarBean = musicControlKitConfig.getMusicToolbar();
        if (toolbarBean != null) {
            // toolbar
            rbvTop.setBlurRadius(toolbarBean.isBlurEnable() ? UiUtils.dp2px(14) : 0);
            rbvTop.setOverlayColor(toolbarBean.getBackgroundColor().getColor());
            UiUtils.setPadding(clTop, toolbarBean.getContentInsets());
            UiUtils.setViewSize(clTop, toolbarBean.getSize());
            // 添加显示的页面
            fragmentList.clear();
            fragmentList.add(MusicListFragment.getInstance(this));
            fragmentList.add(MusicAddFragment.getInstance());
            if (toolbarBean.getMusicControlEnable()) {
                fragmentList.add(MusicControlFragment.getInstance());
            }
            MusicFragmentAdapter adapter = new MusicFragmentAdapter(this);
            vpMusic.setAdapter(adapter);
            vpMusic.setUserInputEnabled(false);
//            vpMusic.setSaveEnabled(false);

            int[] icons = new int[]{R.drawable.rckit_tab_music_list_selector, R.drawable.rckit_tab_music_add_selector, R.drawable.rckit_tab_music_control_selector};
            ImageView imageView;

            List<ImageView> iconList = new ArrayList<>();
            for (int i = 0; i < fragmentList.size(); i++) {
                imageView = new ImageView(getContext());
                UiUtils.setViewSize(imageView, toolbarBean.getTabSize());
                UiUtils.setSelectorImage(imageView, toolbarBean.getTabItem(i), icons[i], RCMusicControlKit.getInstance().getAssetsPath());
                iconList.add(imageView);
            }
            if (tabLayoutMediator == null) {
                RCInsets insets = new RCInsets();
                insets.setRight(toolbarBean.getSpacing());
                tabLayoutMediator = new TabLayoutMediator(tlTab, vpMusic, (tab, position) -> {
                    tab.setCustomView(iconList.get(position));
                    UiUtils.setPadding(tab.view, insets);
                });
                tabLayoutMediator.attach();
            }

            if (toolbarBean.getSoundEffectEnable()) {
                ivEffect.setVisibility(View.VISIBLE);
                ivEffect.setOnClickListener(v -> {
                    if (effectSnackBar == null) {
                        effectSnackBar = EffectSnackBar.make((ViewGroup) getView(), null);
                    }
                    if (effectSnackBar.isShown()) {
                        ivEffect.setSelected(false);
                        effectSnackBar.dismiss();
                    } else {
                        ivEffect.setSelected(true);
                        effectSnackBar.show();
                    }
                });
                UiUtils.setSelectorImage(ivEffect, toolbarBean.getLastTab(), R.drawable.rckit_tab_music_effect_selector, RCMusicControlKit.getInstance().getAssetsPath());
            } else {
                ivEffect.setVisibility(View.INVISIBLE);
            }
        }
        RCAttributes contentAttributes = musicControlKitConfig.getContentAttributes();
        if (contentAttributes != null) {
            rbvBottom.setOverlayColor(contentAttributes.getBackground().getColor());
            rbvBottom.setBlurRadius(contentAttributes.isBlurEnable() ? UiUtils.dp2px(14) : 0);
            UiUtils.setViewSize(vpMusic, contentAttributes.getSize());
        }

        SoundEffectConfig effectConfig = musicControlKitConfig.getSoundEffect();
        if (effectConfig != null) {
            if (effectSnackBar == null) {
                effectSnackBar = EffectSnackBar.make((ViewGroup) getView(), effectConfig);
            }
            RCSize size = new RCSize();
            size.setWidth(effectConfig.getSize().getWidth());
            size.setHeight(effectConfig.getSize().getHeight() + 5);
            UiUtils.setViewSize(space, size);
        }
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
