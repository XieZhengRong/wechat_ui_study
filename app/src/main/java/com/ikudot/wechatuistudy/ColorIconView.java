package com.ikudot.wechatuistudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.core.content.ContextCompat;

public class ColorIconView extends View {
    /**
     * 图标
     */
    private Bitmap iconBitmap;
    /**
     * 图标反色
     */
    private int iconBackgroundColor;
    /**
     * 图标默认背景色
     */
    private final int DEFAULT_ICON_BACKGROUND_COLOR = 0x000000;
    /**
     * 图标绘制范围
     */
    private Rect iconRect;

    private Bitmap mBitmap;
    /**
     * 图标是否取指定反色
     */
    private boolean reverse;


    public ColorIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorIconView);
        BitmapDrawable drawable = (BitmapDrawable) typedArray.getDrawable(R.styleable.ColorIconView_src_icon);
        if (drawable != null) {
            iconBitmap = drawable.getBitmap();
        }
        iconBackgroundColor = typedArray.getColor(R.styleable.ColorIconView_reverse_color, DEFAULT_ICON_BACKGROUND_COLOR);
        reverse = typedArray.getBoolean(R.styleable.ColorIconView_reverse, false);
        //资源回收
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        iconRect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (reverse) {
            //绘制原图标
            canvas.drawBitmap(iconBitmap, null, iconRect, null);
            setupTargetBitmap();
            canvas.drawBitmap(mBitmap, 0, 0, null);

        } else {
            canvas.drawBitmap(iconBitmap, null, iconRect, null);
        }

    }

    /**
     * 在mBitmap上绘制以iconBackgroundColor颜色为Dst，DST_IN模式下的图标
     */
    private void setupTargetBitmap() {
            mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(mBitmap);
            Paint paint = new Paint();
            paint.setColor(iconBackgroundColor);
            paint.setAntiAlias(true);
            paint.setDither(true);
            //在图标背后先绘制一层iconBackgroundColor颜色的背景
            canvas.drawRect(iconRect, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            paint.setAlpha(255);
            //在mBitmap上绘制以iconBackgroundColor颜色为Dst，DST_IN模式下的图标
            canvas.drawBitmap(iconBitmap, null, iconRect, paint);
    }

    public void setIconBitmap(Context context, int resourceID) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(context, resourceID);
        if (!bitmapDrawable.getBitmap().equals(iconBitmap)) {
            iconBitmap = bitmapDrawable.getBitmap();
            invalidateView();
        }
    }

    public void setReverseIconColor(int color) {
        iconBackgroundColor = color;
        invalidateView();
    }

    /**
     * 判断当前是否为UI线程，是则直接重绘，否则调用postInvalidate()利用Handler来重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
        invalidateView();
    }
}
