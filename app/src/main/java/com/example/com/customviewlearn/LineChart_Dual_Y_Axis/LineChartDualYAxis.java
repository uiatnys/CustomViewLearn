package com.example.com.customviewlearn.LineChart_Dual_Y_Axis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
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
        values=new int[30];
        for (int i=0;i<30;i++){
            values[i]=getRandom();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        yLineLength=(int)(height*0.9f)-getPaddingTop();
        xLineLength=width-getPaddingRight()-getPaddingLeft();
        yRelativeValue = (int)(height*0.9f);
        xRelativeValue = getPaddingLeft()+50;
        paint.setColor(0xff696969);
        canvas.drawLine(xRelativeValue,yRelativeValue,xRelativeValue,getPaddingTop(),paint);
        canvas.drawLine(xLineLength,yRelativeValue,xLineLength,getPaddingTop(),paint);
    }

    /**
     * 返回0-100的随机整数
     * @return
     */
    private int getRandom(){
        return (int)(Math.random()*1000);
    }
}
