package com.example.com.customviewlearn.LineChart_Dual_Y_Axis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.com.customviewlearn.R;

/**
 * Created by 99210 on 2016/11/19.
 */

public class LineChartDualYAxis extends View {

    private int width;  //控件宽度
    private int height;  //控件高度
    private int axisTextSize;  //数轴字体大小
    private int lineTextSize;  //折线字体大小
    private int lineHeight;   //折线高度
    private int yRelativeValue;  //原点的相对Y值
    private int xRelativeValue;  //原点的相对X值
    private int yLineLength;   //Y轴长度
    private int xLineLength;    //X轴长度

    private Paint paint;
    private TextPaint textPaint;
    private Path path;
    private int[] values;

    public LineChartDualYAxis(Context context) {
        super(context,null);
    }

    public LineChartDualYAxis(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        if (attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineChartDualYAxis);
            axisTextSize = typedArray.getDimensionPixelSize(R.styleable.LineChartDualYAxis_axis_textsize,10);
            lineTextSize = typedArray.getDimensionPixelSize(R.styleable.LineChartDualYAxis_line_textsize,15);
            lineHeight = typedArray.getDimensionPixelOffset(R.styleable.LineChartDualYAxis_line_height,10);
        }
        paint = new Paint();
        textPaint=new TextPaint();
        path=new Path();
        values=new int[20];
        for (int i=0;i<20;i++){
            values[i]=getRandom();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);
        height= (int) (width*1.2f);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        yLineLength=(int)(height*0.9f)-getPaddingTop();
        xLineLength=width-getPaddingRight()-getPaddingLeft();
        yRelativeValue = (int)(height*0.9f);
        xRelativeValue = getPaddingLeft()+50;
        paint.setAntiAlias(true);
        paint.setColor(0xff696969);
        textPaint.setColor(0xff696969);
        textPaint.setTextSize(axisTextSize);
        canvas.drawLine(xRelativeValue,yRelativeValue,xRelativeValue,getPaddingTop(),paint);
        canvas.drawLine(xLineLength,yRelativeValue,xLineLength,getPaddingTop(),paint);
        for(int i=0;i<=7;i++){
            if (i==0){
                canvas.drawLine(xRelativeValue,yRelativeValue,xLineLength,yRelativeValue,paint);
            }else {
                paint.setColor(0xffBEBEBE);
                canvas.drawLine(xRelativeValue,yRelativeValue-150*i,xLineLength,yRelativeValue-150*i,paint);
            }
            canvas.drawText(i*100+"",getPaddingLeft()-20,yRelativeValue-150*i,textPaint);
            canvas.drawText(i*100+"",xLineLength+10,yRelativeValue-150*i,textPaint);
        }
        path.moveTo(xRelativeValue,yRelativeValue-values[0]);
        float xPosition=xRelativeValue;
        float interval=xLineLength/values.length;
        xPosition+=interval;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffBEBEBE);
        textPaint.setTextSize(lineHeight*10);
        paint.setStrokeWidth(lineHeight*2);
        for(int i=1;i<values.length;i++){
            if (xPosition<xLineLength){
                path.lineTo(xPosition,yRelativeValue-values[i]*1.5f);
                path.moveTo(xPosition,yRelativeValue-values[i]*1.5f);
                canvas.drawPoint(xPosition,yRelativeValue-values[i]*1.5f,paint);
                canvas.drawText(values[i]+"",xPosition-5,yRelativeValue-values[i]*1.5f,textPaint);
            }
            xPosition+=interval;
        }
        paint.setColor(0xff191919);
        paint.setStrokeWidth(lineHeight);
        canvas.drawPath(path,paint);
    }

    /**
     * 返回0-700的随机整数
     * @return
     */
    private int getRandom(){
        return (int)(Math.random()*1000%700);
    }
}
