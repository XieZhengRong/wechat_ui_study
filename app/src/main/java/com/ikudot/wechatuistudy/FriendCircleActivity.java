package com.ikudot.wechatuistudy;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ikudot.wechatuistudy.Util.Utils;
import com.ikudot.wechatuistudy.listener.FriendCircleScrollViewListener;

public class FriendCircleActivity extends AppCompatActivity {
    private static final String TAG = "FriendCircleActivity";
    ImageView userHead; //用户头像
    RelativeLayout topBar; //悬浮标题栏布局
    RelativeLayout backButton; //返回按钮
    ImageView backIcon;//返回按钮图标
    TextView title;//标题文字
    RelativeLayout photoLayout;//发朋友圈按钮
    ColorIconView photoIcon;//发朋友圈按钮图标
    NestedScrollView scrollView;//滑动布局
    int safeTopDistance;//距离顶部安全距离(必须是刘海屏才有)
    int downY;//向下滑动时标题栏渐变点
    int upY;//向上滑动时标题栏渐变点
    int downScrollPart = 1;//下滑阶段 1,2
    int secondDowScrollPartPoint;//下滑第二阶段起点
    int upScrollPart = 1;//上滑阶段 1,2
    int secondUpScrollPartPoint;//上滑第二阶段起点
    boolean topBarBackgroundTransparentMode = true; //当前topBar是否为背景透明模式
    boolean allowUp = false;
    int scrollY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置透明状态栏
        Utils.transparencyBar(this);
        setContentView(R.layout.friend_circle);
        //获取刘海高度
        getSafeTopDistance();
        initViews();
        initData();
        initEvents();
    }

    private void initEvents() {
        FriendCircleScrollViewListener scrollViewListener = new FriendCircleScrollViewListener();
        scrollViewListener.setScrollYListener((scrollY, down) -> {
            this.scrollY = scrollY;
            if (!allowUp) {
                handleScrollDown(scrollY);
            }else {
                handleScrollUp(scrollY);
            }
        });
        //设置scrollView滑动监听
        scrollView.setOnScrollChangeListener(scrollViewListener);
    }

    private void handleScrollDown(int scrollY) {
        if (scrollY > downY) {
            //下滑距离达到 下滑渐变点，则重置上滑阶段为1
            upScrollPart = 1;
            double alpha = 0;
            if (downScrollPart == 1){
                alpha = 1-(scrollY - downY) / (10 * 10.0);
                if (alpha < 0) {
                    alpha = 0;
                    downScrollPart = 2;
                    secondDowScrollPartPoint = scrollY;
                    setTopBarBackgroundLightMode();
                }
            } else if (downScrollPart == 2) {
                //每下滑15像素不透明度增加0.1
                alpha = (scrollY - secondDowScrollPartPoint) / (10 * 10.0);
                //不透明度大于1后不再增加
                if (alpha>1) {
                    alpha = 1;
                    allowUp = true;
                }
                if (alpha <= 0) {
                    setTopBarBackgroundTransparentMode();
                    downScrollPart = 1;
                }
            }
            topBar.setAlpha((float) alpha);
        }
    }

    private void handleScrollUp(int scrollY) {
        if (scrollY < upY) {
            downScrollPart = 1;
            double alpha = 0;
            if (upScrollPart == 1) {
                //每上滑15像素不透明度减少0.1
                alpha = 1 - (upY - scrollY) / (10 * 10.0);
                //不透明度减少到0后，改变标题栏为透明模式
                if (alpha <= 0) {
                    alpha = 0;
                    //设置悬浮标题栏为背景透明模式
                    setTopBarBackgroundTransparentMode();
                    topBarBackgroundTransparentMode = true;
                    //标记为上滑第二阶段
                    upScrollPart = 2;
                    //记住上滑第二阶段起点
                    secondUpScrollPartPoint = scrollY;
                }
            } else if (upScrollPart == 2) {
                /**
                 *进入上滑第二阶段，标题栏改为背景透明模式后慢慢显示
                 *每上滑15像素标题栏不透明度增加0.1
                 */
                alpha = (secondUpScrollPartPoint - scrollY) / (10 * 10.0);
                //不透明度大于1后不再增加
                if (alpha>1) {
                    alpha = 1;
                    allowUp = false;
                }
            }
            topBar.setAlpha((float) alpha);
        }
    }

    private void setTopBarBackgroundTransparentMode() {
        backIcon.setImageResource(R.drawable.b6i);
        backButton.setBackground(getDrawable(R.drawable.back_ripple_bg));
        photoLayout.setBackground(getDrawable(R.drawable.photo_selector));
        photoIcon.setReverse(true);
        photoIcon.setReverseIconColor(getResources().getColor(R.color.white));
        title.setVisibility(View.GONE);
        topBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        Utils.transparencyBar(FriendCircleActivity.this);
    }

    private void setTopBarBackgroundLightMode() {
        backIcon.setImageResource(R.drawable.b6c);
        backButton.setBackground(getDrawable(R.drawable.photo_light_mode_selector));
        photoLayout.setBackground(getDrawable(R.drawable.photo_light_mode_selector));
        photoIcon.setReverse(true);
        photoIcon.setReverseIconColor(getResources().getColor(R.color.black));
        title.setVisibility(View.VISIBLE);
        title.setTextColor(getResources().getColor(R.color.black));
        topBar.setBackgroundColor(getResources().getColor(R.color.statusBarColors));

        Utils.StatusBarLightMode(FriendCircleActivity.this);
    }

    private void initViews() {
        userHead = findViewById(R.id.friend_circle_user_head);
        topBar = findViewById(R.id.friend_circle_top_bar);
        backButton = findViewById(R.id.friend_circle_back);
        backIcon = findViewById(R.id.friend_circle_back_icon);
        title = findViewById(R.id.friend_circle_back_title);
        photoIcon = findViewById(R.id.friend_circle_photo_icon);
        photoLayout = findViewById(R.id.friend_circle_photo_layout);
        scrollView = findViewById(R.id.friend_circle_scroll_view);
        title.setVisibility(View.GONE);
        //根据屏幕刘海高度动态设置标题栏padding_top属性，以防刘海遮挡内容
        topBar.post(new Runnable() {
            @Override
            public void run() {
                topBar.setPadding(0, safeTopDistance, 0, 0);
            }
        });
    }

    private void initData() {
        downY = Utils.dpTpPx(190, FriendCircleActivity.this);
        upY = Utils.dpTpPx(230, FriendCircleActivity.this);
        Glide.with(this)
                .load("https://img-blog.csdnimg.cn/20190918140145169.png")
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(Utils.dpTpPx(6, FriendCircleActivity.this))))//圆角半径
                .into(userHead);
    }

    @TargetApi(28)
    public void getSafeTopDistance() {
        final View decorView = getWindow().getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                safeTopDistance = displayCutout.getSafeInsetTop();
            }
        });
    }

    private int touchY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (scrollY == 0) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:{
                    touchY = (int) event.getRawY();
                }break;
                case MotionEvent.ACTION_MOVE:{
                    int moveY = (int) (event.getRawY() - touchY);
                    scrollView.setTranslationY(moveY);
                }break;
                case MotionEvent.ACTION_UP:{
                    scrollView.setTranslationY(0);
                }break;
            }
        }

        return super.onTouchEvent(event);

    }
}
