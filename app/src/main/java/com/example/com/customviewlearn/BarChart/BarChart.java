package com.example.com.customviewlearn.BarChart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.com.customviewlearn.R;

import java.util.Random;

/**
 * Created by WangZH on 2016/11/14.
 */

public class BarChart extends View {

    private int width;   //控件宽度
    private int height;   //控件高度
    private int bottomTextSize;  //X轴底部文字大小
    private int barWidth;   //柱宽度
    private int barInterval;  //柱间隔
    private int yLineLength;   //Y轴长度
    private int xLineLength;    //X轴长度

    private Paint paint;

    public BarChart(Context context) {
        super(context);
        init(context,null);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        if (attrs!=null){
            TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.BarChart);
            bottomTextSize = (int) typedArray.getDimension(R.styleable.BarChart_bottom_textsize,20);
            barWidth = (int)typedArray.getDimension(R.styleable.BarChart_bar_width,10);
            barInterval = (int)typedArray.getDimension(R.styleable.BarChart_bar_interval,10);
        }
        paint = new Paint();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = (int)(width*1.2f);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(0xff000000);
        paint.setStrokeWidth(1.5f);
        //绘制坐标轴
        canvas.drawLine(getPaddingLeft()+50,(int)(height*0.9f),width-getPaddingRight(),(int)(height*0.9f),paint);
        canvas.drawLine(getPaddingLeft()+50,getPaddingTop(),getPaddingLeft()+50,(int)(height*0.9f),paint);
        for (int i=0;i<4;i++){
            paint.setColor(0xff7FFFD4);
        }
    }

    private int getRandom(){
        return (int)(Math.random()*100);
    }
}
