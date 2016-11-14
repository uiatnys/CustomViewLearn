package com.example.com.customviewlearn.Test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WangZH on 2016/11/14.
 */

public class CustomViewGroup1 extends ViewGroup {

    public CustomViewGroup1(Context context) {
        super(context);
    }

    public CustomViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomViewGroup1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //如果宽高都是包裹内容
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //我们将高度设置为所有子View的高度相加，宽度设为子View中最大的宽度
            int height = getTotleHeight();
            int width = getMaxChildWidth();
            setMeasuredDimension(width, height);

        } else if (heightMode == MeasureSpec.AT_MOST) {//如果只有高度是包裹内容
            //宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
            setMeasuredDimension(widthSize, getTotleHeight());
        } else if (widthMode == MeasureSpec.AT_MOST) {//如果只有宽度是包裹内容
            //宽度设置为子View中宽度最大的值，高度设置为ViewGroup自己的测量值
            setMeasuredDimension(getMaxChildWidth(), heightSize);

        }
    }

    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth)
                maxWidth = childView.getMeasuredWidth();

        }

        return maxWidth;
    }

    /***
     * 将所有子View的高度相加
     **/
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();

        }

        return height;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前的高度位置
        int curHeight = t;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            child.layout(l, curHeight, l + width, curHeight + height);
            curHeight += height;
        }
    }
}
