package com.example.com.customviewlearn;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 99210 on 2016/11/13.
 */

public class CustomView1 extends View {

    private int width;   //控件宽度
    private int height;   //控件高度
    private Paint paint;
    private int defaultTextSize;  //默认字体大小

    public CustomView1(Context context) {
        super(context);
        init(context,null);
    }

    public CustomView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomView1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    public CustomView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        paint=new Paint();
        if (attrs!=null){
            TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.CustomView1);
            defaultTextSize=typedArray.getDimensionPixelSize(R.styleable.CustomView1_default_text_size,30);
        }
    }


    /**
     * 测量模式	表示意思
     UNSPECIFIED	父容器没有对当前View有任何限制，当前View可以任意取尺寸
     EXACTLY	当前的尺寸就是当前View应该取的尺寸
     AT_MOST	当前尺寸是当前View能取的最大尺寸
     * @param defaultSize
     * @param measureSpec
     * @return
     */
    private int getViewSize(int defaultSize,int measureSpec){
        int defaultSize1=defaultSize;
        int mode=MeasureSpec.getMode(measureSpec);
        int size=MeasureSpec.getSize(measureSpec);

        switch (mode){
            case MeasureSpec.UNSPECIFIED:
                defaultSize1=defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                defaultSize1=size;
                break;
            case MeasureSpec.EXACTLY:
                defaultSize1=size;
                break;
        }
        return defaultSize1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec); //getViewSize(100,widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec); //getViewSize(100,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=getMeasuredWidth()/2;
        int height=getMeasuredHeight()/2;
        int radius=getMeasuredHeight()/3;
        paint.setColor(0xff528B8B);
        canvas.drawCircle(width,height,radius,paint);
        paint.setTextSize(defaultTextSize);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Circle",width,getMeasuredHeight()-10,paint);
    }
}
