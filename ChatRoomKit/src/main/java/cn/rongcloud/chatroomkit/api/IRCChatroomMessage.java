package cn.rongcloud.chatroomkit.api;

import android.text.SpannableStringBuilder;

import cn.rongcloud.corekit.bean.Argb;
import cn.rongcloud.corekit.bean.Corner;

/**
 * Created by hugo on 2021/11/18
 */
public interface IRCChatroomMessage {
    /**
     * 气泡背景色，返回null采用默认背景
     *
     * @return {@link Argb}颜色
     */
    Argb bubbleColor();

    /**
     * 气泡背景色，返回null采用默认背景
     *
     * @return {@link Argb}颜色
     */
    Argb bubbleTextColor();

    /**
     * 气泡圆角，返回null采用默认圆角
     *
     * @return {@link Corner}圆角
     */
    Corner bubbleCorner();

    /**
     * 消息内容富文本，可以在富文本中自定义点击事件
     *
     * @return 返回SpannableStringBuilder
     */
    SpannableStringBuilder buildMessage(OnMessageContentClickListener clickSpan);
}
