package com.ikudot.wechatuistudy;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ikudot.wechatuistudy.Util.Utils;
import com.ikudot.wechatuistudy.databinding.FriendCircleBinding;
import com.ikudot.wechatuistudy.listener.FriendCircleScrollViewListener;

public class FriendCircleActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "FriendCircleActivity";
    int safeTopDistance;//距离顶部安全距离(必须是刘海屏才有)
    int downY;//向下滑动时标题栏渐变点
    int upY;//向上滑动时标题栏渐变点
    int downScrollPart = 1;//下滑阶段 1,2
    int secondDowScrollPartPoint;//下滑第二阶段起点
    int upScrollPart = 1;//上滑阶段 1,2
    int secondUpScrollPartPoint;//上滑第二阶段起点
    boolean topBarBackgroundTransparentMode = true; //当前topBar是否为背景透明模式
    boolean allowUp = false;//是否允许处理 scrollView向上滚动时悬浮标题栏的渐变效果，为false时，只处理向下滚动时的效果，为true时，只处理向上滚动时的效果
    int scrollY;//scrollView向下滚动距离
    int tempDistance;//移动布局离原位置Y轴距离
    boolean everTranslateY = false;//用户是否曾经向下拖动过布局
    private float touchY;//用户手指按下点的Y轴坐标
    FriendCircleBinding binding;//视图绑定对象
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置透明状态栏
        Utils.transparencyBar(this);
        //获取布局加载器
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        //获取ViewBinding对象，免去繁琐的findViewById
        binding = FriendCircleBinding.inflate(layoutInflater);
        //binding.getRoot()返回绑定布局的根视图
        setContentView(binding.getRoot());
        //获取刘海高度
        getSafeTopDistance();
        init();
        initData();
        initEvents();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //记住按下时手指Y轴坐标
                touchY = event.getRawY();
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                float moveY = (event.getRawY() - touchY);
                Log.d(TAG, "onTouch: setTranslationY" + binding.friendCircleMoveLayout.getTranslationY());
                //当滑动距离为0.即没有滑动的时候才能向下移动布局
                if (scrollY <= 0) {
                    binding.friendCircleMoveLayout.setTranslationY(moveY * 2 / 5 + tempDistance);
                }
                //如果TranslationY>0,证明此次向下拖动过布局，不是一开始就向下滚动
                if (binding.friendCircleMoveLayout.getTranslationY() > 0) {
                    everTranslateY = true;
                }
                if (binding.friendCircleMoveLayout.getTranslationY() > 0) {
                    //TranslationY>0，即布局是向下拖动状态，return true阻止scrollView向下滚动
                    return true;
                } else {
                    //用户曾经向下拖动过布局（即everTranslateY=true），再手动将布局拖回原位后不允许接着滚动scrollView，TranslationY=0时即布局拖回原位，TranslationY<0时即拖回原位后继续往上拖
                    if (binding.friendCircleMoveLayout.getTranslationY() <= 0 && everTranslateY) {
                        //不允许继续往上拖，保持布局在原位置·
                        binding.friendCircleMoveLayout.setTranslationY(0);
                        return true;
                    }else {
                        //everTranslateY=false 即用户没有向下拖动过布局，正常向下滚动scrollView
                        return false;
                    }
                }
            }
            case MotionEvent.ACTION_UP: {
                //重置用户是否向下拖动过布局的标记
                everTranslateY = false;
                //用户手指离开屏幕，复位布局
                binding.friendCircleMoveLayout.setTranslationY(0);
                //用户手指离开屏幕，复位下拉距离
                tempDistance = 0;
            }
            break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                //当第一个手指按下后，有其他手指按下，则记住当前移动布局离原位置距离，以便累加新手指的拖动距离
                tempDistance = (int) binding.friendCircleMoveLayout.getTranslationY();
                //当第一个手指按下后，有其他手指按下，则更新用户按下点Y轴坐标
                touchY = event.getRawY();
            }
            break;
        }
        return false;
    }



    private void initEvents() {
        FriendCircleScrollViewListener scrollViewListener = new FriendCircleScrollViewListener();
        scrollViewListener.setScrollYListener((scrollY, down) -> {
            this.scrollY = scrollY;
            if (!allowUp) {
                //处理下滑悬浮标题效果
                handleScrollDown(scrollY);
            } else {
                //处理赏花悬浮标题效果
                handleScrollUp(scrollY);
            }
        });
        //设置scrollView滑动监听
        binding.friendCircleScrollView.setOnScrollChangeListener(scrollViewListener);
        //设置scrollView手势监听
        binding.friendCircleScrollView.setOnTouchListener(this);
    }

    private void handleScrollDown(int scrollY) {
        if (scrollY > downY) {
            //下滑距离达到 下滑渐变点，则重置上滑阶段为1
            upScrollPart = 1;
            double alpha = 0;
            if (downScrollPart == 1) {
                alpha = 1 - (scrollY - downY) / (10 * 10.0);
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
                if (alpha > 1) {
                    alpha = 1;
                    allowUp = true;
                }
                if (alpha <= 0) {
                    setTopBarBackgroundTransparentMode();
                    downScrollPart = 1;
                }
            }
            binding.friendCircleTopBar.setAlpha((float) alpha);
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
                if (alpha > 1) {
                    alpha = 1;
                    allowUp = false;
                }
                if (alpha <= 0) {
                    setTopBarBackgroundLightMode();
                    upScrollPart = 1;
                }
            }
            binding.friendCircleTopBar.setAlpha((float) alpha);
        }
    }

    private void setTopBarBackgroundTransparentMode() {
        binding.friendCircleBackIcon.setImageResource(R.drawable.b6i);
        binding.friendCircleBack.setBackground(getDrawable(R.drawable.back_ripple_bg));
        binding.friendCirclePhotoLayout.setBackground(getDrawable(R.drawable.photo_selector));
        binding.friendCirclePhotoIcon.setReverse(true);
        binding.friendCirclePhotoIcon.setReverseIconColor(getResources().getColor(R.color.white));
        binding.friendCircleBackTitle.setVisibility(View.GONE);
        binding.friendCircleTopBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        Utils.transparencyBar(FriendCircleActivity.this);
    }

    private void setTopBarBackgroundLightMode() {
        binding.friendCircleBackIcon.setImageResource(R.drawable.b6c);
        binding.friendCircleBack.setBackground(getDrawable(R.drawable.photo_light_mode_selector));
        binding.friendCirclePhotoLayout.setBackground(getDrawable(R.drawable.photo_light_mode_selector));
        binding.friendCirclePhotoIcon.setReverse(true);
        binding.friendCirclePhotoIcon.setReverseIconColor(getResources().getColor(R.color.black));
        binding.friendCircleBackTitle.setVisibility(View.VISIBLE);
        binding.friendCircleBackTitle.setTextColor(getResources().getColor(R.color.black));
        binding.friendCircleTopBar.setBackgroundColor(getResources().getColor(R.color.statusBarColors));
        Utils.StatusBarLightMode(FriendCircleActivity.this);
    }

    private void init() {
        binding.friendCircleBackTitle.setVisibility(View.GONE);
        //根据屏幕刘海高度动态设置标题栏padding_top属性，以防刘海遮挡内容
        binding.friendCircleTopBar.post(new Runnable() {
            @Override
            public void run() {
                binding.friendCircleTopBar.setPadding(0, safeTopDistance, 0, 0);
            }
        });
    }

    private void initData() {
        downY = Utils.dpTpPx(180, FriendCircleActivity.this);
        upY = Utils.dpTpPx(230, FriendCircleActivity.this);
        Glide.with(this)
                .load("https://img-blog.csdnimg.cn/20190918140145169.png")
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(Utils.dpTpPx(6, FriendCircleActivity.this))))//圆角半径
                .into(binding.friendCircleUserHead);
    }

    @TargetApi(28)
    public void getSafeTopDistance() {
        final View decorView = getWindow().getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                if (displayCutout != null) {
                    safeTopDistance = displayCutout.getSafeInsetTop();
                }
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}
