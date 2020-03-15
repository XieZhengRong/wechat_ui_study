package com.ikudot.wechatuistudy;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FriendCircleActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "FriendCircleActivity";
    int safeTopDistance;//距离顶部安全距离(必须是刘海屏才有)
    int downY;//向下滑动时标题栏渐变点Y轴坐标
    int upY;//向上滑动时标题栏渐变点Y轴坐标
    int downScrollPart = 1;//下滑阶段 1,2，默认值为 1
    int secondDowScrollPartPoint;//下滑第二阶段起点Y轴坐标
    int upScrollPart = 1;//上滑阶段 1,2
    int secondUpScrollPartPoint;//上滑第二阶段起点Y轴坐标
    boolean topBarBackgroundTransparentMode = true; //当前topBar是否为背景透明模式
    boolean allowUp = false;//是否允许处理 scrollView向上滚动时悬浮标题栏的渐变效果，为false时，只处理向下滚动时的效果，为true时，只处理向上滚动时的效果
    int scrollY;//scrollView向下滚动距离
    boolean everTranslateY = false;//用户是否曾经向下拖动过布局
    FriendCircleBinding binding;//视图绑定对象
    private boolean downOnuUserHead = false;//用户按下时手指触摸点是否在用户头像上
    Map<Integer, Float> touchYMap = new HashMap<>();//记录所有按下手指的 id（Map的Key）和按下点的Y轴坐标（Map的Value）

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
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                //记住第一个点按下时手指Y轴坐标和id
                touchYMap.put(event.getPointerId(event.getActionIndex()), event.getY());
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                int pointCount = event.getPointerCount();
                //布局拖动距离
                float moveY = 0;
                //布局拖动距离等于所有手指拖动距离的总和
                for (int i = 0; i < pointCount; i++) {
                    //遍历所有手指的按下Y轴坐标，和当前拖动时的Y轴坐标相减，即是此手指的移动距离
                    Float pointTouchY = touchYMap.get(event.getPointerId(i));
                    //累加所有手指的移动距离
                    moveY += event.getY(i) - pointTouchY;
                }
                //当滑动距离为0.即没有滑动的时候才能向下移动布局
                if (scrollY <= 0 && !downOnuUserHead) {
                    //为制造阻力效果，所以2/5.实测这时和微信的感觉差不多
                    float distance = moveY * 2 / 5;
                    binding.friendCircleMoveLayout.setTranslationY(distance);
                }
                if (binding.friendCircleMoveLayout.getTranslationY() > 0) {
                    //如果TranslationY>0,证明此次向下拖动过布局，不是一开始就向下滚动
                    everTranslateY = true;
                    //TranslationY>0，即布局是向下拖动状态，return true阻止scrollView向下滚动
                    return true;
                } else {
                    //用户曾经向下拖动过布局（即everTranslateY=true），再手动将布局拖回原位后，不允许接着滚动scrollView，TranslationY=0时即布局拖回原位，TranslationY<0时即拖回原位后继续往上拖
                    if (binding.friendCircleMoveLayout.getTranslationY() <= 0 && everTranslateY) {
                        //不允许继续往上拖，保持布局在原位置·
                        binding.friendCircleMoveLayout.setTranslationY(0);
                        return true;
                    } else {
                        //everTranslateY=false 即用户没有向下拖动过布局，正常向下滚动scrollView
                        return false;
                    }
                }
            }
            case MotionEvent.ACTION_UP: {
                //重置用户是否向下拖动过布局的标记
                everTranslateY = false;
                //用户最后一个触摸点离开屏幕，播放动画，复位布局
                ObjectAnimator animator = ObjectAnimator.ofFloat(binding.friendCircleMoveLayout, "translationY", binding.friendCircleMoveLayout.getTranslationY(), 0);
                animator.setDuration(200);
                animator.start();
                //最后一个点抬起，清空所有手指位置信息
                touchYMap.clear();
            }
            break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                //记录新按下手指的id和Y轴坐标
                touchYMap.put(event.getPointerId(event.getActionIndex()), event.getY(event.getActionIndex()));
                Log.d(TAG, "ACTION_POINTER_DOWN:touchMap= " + touchYMap);
//                Log.d(TAG, "ACTION_POINTER_DOWN:touchYList= "+touchYList);
            }
            break;
            case MotionEvent.ACTION_POINTER_UP: {
                //获取当前离开屏幕手指点的Y轴坐标
                float upPointerY = event.getY(event.getActionIndex());
                //获取离开屏幕手指的id
                int pointId = event.getPointerId(event.getActionIndex());
                //计算当前手指滑动的距离
                float upPointerMoveDistance = upPointerY - touchYMap.get(pointId);
                //移除此点数据
                touchYMap.remove(pointId);
                //获取移除离开当前手指后剩下手指的id迭代器
                Iterator<Integer> iterator = touchYMap.keySet().iterator();
                //获取还在屏幕上的手指数量，因为已经移除一个手指，所以减一
                int pointerCount = event.getPointerCount() - 1;
                //为了保持布局拖动连贯性，剩下的点将平分当前抬起点的拖动距离
                float perPointerDistributeDistance = upPointerMoveDistance / pointerCount;
                while (iterator.hasNext()) {
                    int positionId = iterator.next();
                    //平分抬起点拖动距离，等价于将所有点按下位置加上平分的距离，因为moveY是所有点移动时Y轴坐标减去相应点按下时的Y轴坐标
                    touchYMap.put(positionId, touchYMap.get(positionId) - perPointerDistributeDistance);
                }
//                Log.d(TAG, "ACTION_POINTER_UP:upPointerMoveDistance= " + upPointerMoveDistance);
//                Log.d(TAG, "ACTION_POINTER_UP:touchMap= " + touchYMap);
            }
            break;

        }
        return false;
    }


    @SuppressLint("ClickableViewAccessibility")
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
        binding.friendCircleUserHead.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        downOnuUserHead = true;

                        Log.d(TAG, "onTouch:ACTION_DOWN " + downOnuUserHead);

                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        downOnuUserHead = false;
                        Log.d(TAG, "onTouch:ACTION_UP " + downOnuUserHead);
                    }
                    break;
                    case MotionEvent.ACTION_CANCEL: {
                        downOnuUserHead = false;
                        touchYMap.put(event.getPointerId(event.getActionIndex()), event.getY());
                        Log.d(TAG, "onTouch:ACTION_CANCEL " + downOnuUserHead);
                    }
                }
                return false;
            }
        });
        //双击标题栏滚动到最顶部
        binding.friendCircleTopBar.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onDoubleClick(View v) {
                binding.friendCircleScrollView.smoothScrollTo(0, 0);
            }
        });
    }
    /**
     * 处理下滑悬浮标题栏渐变效果
     * @param scrollY
     */
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
                    //当用户进入下滑第二阶段后，突然向上滑动，当标题完全透明时切换为下滑第一阶段并切换标题栏为背景透明模式
                    setTopBarBackgroundTransparentMode();
                    downScrollPart = 1;
                }
            }
            binding.friendCircleTopBar.setAlpha((float) alpha);
        }
    }

    /**
     * 处理上滑悬浮标题栏渐变效果
     * @param scrollY
     */
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
                //进入上滑第二阶段，标题栏改为背景透明模式后慢慢显示
                //每上滑15像素标题栏不透明度增加0.1
                alpha = (secondUpScrollPartPoint - scrollY) / (10 * 10.0);
                //不透明度大于1后不再增加
                if (alpha > 1) {
                    alpha = 1;
                    allowUp = false;
                }
                if (alpha <= 0) {
                    //当用户进入上滑第二阶段后，突然向下滑动，当标题完全透明时切换为上滑第一阶段，切换标题栏为背景明亮模式
                    setTopBarBackgroundLightMode();
                    upScrollPart = 1;
                }
            }
            binding.friendCircleTopBar.setAlpha((float) alpha);
        }
    }

    /**
     * 设置标题栏为背景透明模式
     */
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
    /**
     * 设置标题栏为背景明亮模式
     */
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
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(Utils.dpTpPx(6, FriendCircleActivity.this))))//圆角半径
                .into(binding.friendCircleUserHead);
    }

    @TargetApi(28)
    public void getSafeTopDistance() {
        final View decorView = getWindow().getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取屏幕刘海信息
                    DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                    //若不是刘海屏，displayCutout可能为null
                    if (displayCutout != null) {
                        safeTopDistance = displayCutout.getSafeInsetTop();
                    }
                    //若不是刘海屏，则悬浮标题栏离屏幕顶部的安全距离应该是状态栏高度
                    if (safeTopDistance == 0) {
                        //获取状态栏高度
                        safeTopDistance = Utils.getStatusBarHeight(FriendCircleActivity.this);
                    }
                } catch (NoSuchMethodError error) {
                    error.printStackTrace();
                    safeTopDistance = Utils.getStatusBarHeight(FriendCircleActivity.this);
                }
            }
        });
    }

    public abstract static class DoubleClickListener implements View.OnClickListener {
        private static final long DOUBLE_TIME = 1000;
        private static long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
                onDoubleClick(v);
            }
            lastClickTime = currentTimeMillis;
        }

        public abstract void onDoubleClick(View v);

    }
}
