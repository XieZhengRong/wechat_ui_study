package com.ikudot.wechatuistudy;

import android.view.View;
import android.widget.FrameLayout;

class ViewWrapper{
        View target;
        public ViewWrapper(View view){
            this.target = view;
        }
        public int getMarginTop(){
            return ((FrameLayout.LayoutParams)target.getLayoutParams()).topMargin;
        }
        public void setMarginTop(int marginTop){
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) target.getLayoutParams();
            lp.topMargin = marginTop;
            target.setLayoutParams(lp);
            //注意此处只设置setLayoutParams并不能带来UI上的改变，必须在最后调用requestLayout
            //这就是属性动画原理中说到的第二个条件
            target.requestLayout();
        }
    }