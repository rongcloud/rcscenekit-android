package cn.rongcloud.chatroomkit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;

import cn.rongcloud.chatroomkit.R;
import cn.rongcloud.chatroomkit.bean.InputBarBean;
import cn.rongcloud.corekit.api.RCSceneKitEngine;
import cn.rongcloud.corekit.utils.SoftKeyboardUtils;
import cn.rongcloud.corekit.utils.UiUtils;
import cn.rongcloud.corekit.utils.VMLog;


/**
 * Created by hugo on 2021/11/12
 */
public class InputBar extends LinearLayout {
    private final static String TAG = VMLog.getTag(InputBar.class);
    private EmojiEditText etInput;
    private Space space1;
    private ImageView ivEmoji;
    private Space space2;
    private TextView tvSend;
    private InputBarListener inputBarListener;
    /**
     * emoji选择框
     */
    private EmojiPopup mEmojiPopup;

    public InputBar(@NonNull Context context) {
        this(context, null);
    }

    public InputBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.rckit_inputbar, this);
        initView();
    }

    private void initView() {
        // init view
        this.setOrientation(HORIZONTAL);
        etInput = (EmojiEditText) findViewById(R.id.et_input);
        space1 = (Space) findViewById(R.id.space1);
        ivEmoji = (ImageView) findViewById(R.id.iv_emoji);
        space2 = (Space) findViewById(R.id.space2);
        tvSend = (TextView) findViewById(R.id.tv_send);

        InputBarBean inputBarBean = RCSceneKitEngine.getInstance().getKitConfig(InputBarBean.class);
        if (inputBarBean == null) {
            VMLog.e(TAG, "initView failed : inputBarBean is null");
            return;
        }
        // parent
        this.setBackgroundColor(inputBarBean.getBackgroundColor().getColor());
        UiUtils.setPadding(this, inputBarBean.getContentInsets());
        // input
        etInput.setBackground(UiUtils.createRectangleDrawable(inputBarBean.getInputBackgroundColor().getColor(), 0, 0, dp2px(inputBarBean.getInputCorner())));
        UiUtils.setPadding(etInput, inputBarBean.getInputInsets());
        etInput.setMinHeight(dp2px(inputBarBean.getInputMinHeight()));
        etInput.setMaxHeight(dp2px(inputBarBean.getInputMaxHeight()));
        etInput.setTextSize(inputBarBean.getInputTextSize());
        etInput.setTextColor(inputBarBean.getInputTextColor().getColor());
        etInput.setHint(inputBarBean.getInputHint());
        etInput.setHintTextColor(inputBarBean.getInputHintColor().getColor());
        // emoji
        if (inputBarBean.getEmojiEnable()) {
            space1.setVisibility(VISIBLE);
            ivEmoji.setVisibility(VISIBLE);
        } else {
            space1.setVisibility(GONE);
            ivEmoji.setVisibility(GONE);
        }
        mEmojiPopup = EmojiPopup
                .Builder
                .fromRootView(this)
                .setKeyboardAnimationStyle(R.style.emoji_fade_animation_style)
                .setOnEmojiPopupShownListener(() -> {
                    ivEmoji.setImageResource(R.drawable.rckit_ic_input);
                })
                .setOnEmojiPopupDismissListener(() -> {
                    ivEmoji.setImageResource(R.drawable.rckit_ic_emoji);
                }).build(etInput);
        ivEmoji.setOnClickListener(v -> {
            if (inputBarListener != null) {
                boolean intercept = inputBarListener.onClickEmoji();
                if (intercept) {

                } else {
                    mEmojiPopup.toggle();
                }
            }
        });
        tvSend.setOnClickListener(v -> {
            String message = "";
            if (etInput.getText() != null) {
                message = etInput.getText().toString().trim();
            }
            etInput.setText("");

            if (inputBarListener != null) {
                inputBarListener.onClickSend(message);
            }
        });
    }

    private int dp2px(int dp) {
        return UiUtils.dp2px(getContext(), dp);
    }

    public void showInputBar() {
        this.setVisibility(VISIBLE);
        etInput.requestFocus();
        SoftKeyboardUtils.showSoftKeyboard(etInput, 200);
    }

    public void hideInputBar() {
        mEmojiPopup.dismiss();
        this.setVisibility(GONE);
        etInput.clearFocus();
        SoftKeyboardUtils.hideSoftKeyboard(etInput);
    }

    public void setInputBarListener(InputBarListener inputBarListener) {
        this.inputBarListener = inputBarListener;
    }

    public interface InputBarListener {

        void onClickSend(String message);

        boolean onClickEmoji();
    }
}
