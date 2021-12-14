package cn.rongcloud.musiccontrolkit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.rongcloud.musiccontrolkit.R;

/**
 * Created by gyn on 2021/11/24
 */
public class MusicPlayingView extends View {

    private int columnNum = 4;
    private int random = 0;
    private boolean isStart = true;
    private Random mRandom = new Random();


    private Paint mPaint = new Paint();
    private int mWidth = 0;
    private int mHeight = 0;
    private float mRectWidth = 0.0f;
    private long during = 180L;


    private List<RectF> rectList = new ArrayList<>();
    private List<Integer> randomList = new ArrayList<>();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshHeight(null);
            invalidate();
        }
    };

    public MusicPlayingView(Context context) {
        this(context, null);
    }

    public MusicPlayingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MusicPlayingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RCKitMusicPlayView);
        mPaint.setColor(array.getColor(R.styleable.RCKitMusicPlayView_line_color,
                ContextCompat.getColor(context, android.R.color.black)));
        during = array.getInteger(R.styleable.RCKitMusicPlayView_refresh_duration, 180);
        columnNum = array.getInteger(R.styleable.RCKitMusicPlayView_line_number, 4);

        array.recycle();

        mPaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < columnNum; i++) {
            rectList.add(new RectF());
        }
    }

    public void setLineColor(int color) {
        mPaint.setColor(color);
    }

    private void refreshHeight(Integer height) {
        randomList.clear();
        for (int i = 0; i < columnNum; i++) {
            if (height == null) {
                randomList.add(mRandom.nextInt(random));
            } else {
                randomList.add(height);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mRectWidth = mWidth / (2 * columnNum + 1);
        random = mHeight / columnNum;
        refreshHeight(null);
    }

    public void start() {
        if (isStart) {
            return;
        }
        isStart = true;
        invalidate();
    }

    public void stop() {
        if (!isStart) {
            return;
        }
        isStart = false;
        invalidate();
    }

    public boolean isStart() {
        return isStart;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isStart) {
            postDelayed(runnable, during);
        } else {
            removeCallbacks(runnable);
            refreshHeight(0);
        }

        for (int i = 0; i < rectList.size(); i++) {
            rectList.get(i).set((float) (mRectWidth * (i * 2 + 1)),
                    (float) (randomList.get(i) * rectList.size()),
                    (float) (mRectWidth * (i + 1) * 2),
                    (float) mHeight);
        }
        for (RectF rectF : rectList) {
            canvas.drawRect(rectF, mPaint);
        }
    }
}
