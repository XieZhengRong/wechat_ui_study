package com.ikudot.wechatuistudy.listener;
import androidx.core.widget.NestedScrollView;

/**
 * @author XieZhengRong
 * @Description 自定义NestedScrollView滑动监听类
 */
public class FriendCircleScrollViewListener implements NestedScrollView.OnScrollChangeListener {
    private OnScrollYListener scrollYListener;
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollYListener != null) {
            /**
             * oldScrollY 是滑动前Y轴偏移量，scrollY 是滑动后Y轴偏移量
             * 所以scrollY-oldScrollY>0即正在向下滑，反之向上滑
             * 回调Y轴方向滑动距离ScrollY和当前滑动方向，true为正在向下滑，false为正在向上滑
             */
            scrollYListener.onScrollY(scrollY,scrollY - oldScrollY > 0);
        }
    }



    /**
     * 自定义Y轴偏移量和滑动方向监听器
     */
   public interface OnScrollYListener{
        void onScrollY(int scrollY,boolean down);
    }

    public void setScrollYListener(OnScrollYListener scrollYListener) {
        this.scrollYListener = scrollYListener;
    }
}
