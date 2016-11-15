package com.example.com.customviewlearn.BarChart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
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
    private int yRelativeValue;   //原点的相对y值
    private int xRelativeValue;   //原点的相对X值

    private Paint paint;
    private TextPaint textPaint;

    private final int[] colors = new int[]{0xff32CD32,0xffFFD700,0xffFF3030,0xff1E90FF};

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
        textPaint=new TextPaint();
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
        paint.setStrokeWidth(1.5f);
        yLineLength=(int)(height*0.9f)-getPaddingTop();
        xLineLength=width-getPaddingRight()-getPaddingLeft()+50;
        yRelativeValue = (int)(height*0.9f);
        xRelativeValue = getPaddingLeft()+50;
        textPaint.setColor(0xff696969);
        textPaint.setTextSize(30);
        //绘制X轴
        for (int i=0;i<11;i++) {
            if (i==0){
                paint.setColor(0xff000000);
            }else{
                paint.setColor(0xffBEBEBE);
            }
            canvas.drawLine(xRelativeValue, yRelativeValue-100*(i), width - getPaddingRight(), yRelativeValue-100*(i), paint);
            canvas.drawText((i*100)+"",getPaddingLeft()-20,yRelativeValue-100*(i),textPaint);
        }
        //Y轴
        paint.setColor(0xff000000);
        canvas.drawLine(xRelativeValue,getPaddingTop(),xRelativeValue,yRelativeValue,paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        float l=xRelativeValue+barInterval;
        float r=xRelativeValue+barInterval+barWidth;
        //绘制底部图例
        for (int i=0;i<4;i++){
            paint.setColor(colors[i]);
            canvas.drawRect(l+50*i,yRelativeValue+50,r+50*i,yRelativeValue+80,paint);
            if (i==3){
                canvas.drawText("图例",l+50*(i+1),yRelativeValue+80,textPaint);
            }
        }
        //绘制柱状图
        int index=0;
        for (int j=0;j<4;j++) {
            for (int i = 1; i <= 4; i++) {
                paint.setColor(colors[i - 1]);
                canvas.drawRect(l
                        , yRelativeValue - getRandom()
                        , r
                        , yRelativeValue, paint);
                index += 1;
                canvas.drawText(index+"",l,yRelativeValue+35,textPaint);
                l = r + barInterval;
                r = r + barInterval + barWidth;
            }
        }

    }

    /**
     * 返回0-1000的随机整数
     * @return
     */
    private int getRandom(){
        return (int)(Math.random()*1000);
    }
}
