package com.example.com.customviewlearn.BarChart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.com.customviewlearn.R;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by WangZH on 2016/11/14.
 * 效果：柱状图可点击
 * 2016-11.15已知问题：
 * 柱状图的点击和柱状图的滑动只能二选一，原因是滑动后偏移与onDraw未处理好
 * 使用滑动功能： // BarChart.this.scrollBy((int) (distanceX * scroll_rate), 0);  这个方法取消注释，同时注释onSingleTapUp中的内容
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
    private int offset_x = 0;		// 移动的时候 x轴方向上 的偏移量  获取 点击区域的时候用
    private double scroll_rate = 0.7;//拖动灵敏度
    private int currentTouchIndexX=0;      //当前点击的柱的x位置
    private int currentTouchIndexY=0;      //当前点击的柱的y高度

    private GestureDetector mGestureListener = null;
    private Map<Integer,Integer> values;

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
        mGestureListener = new GestureDetector(new MyGestureListener());
        values=new LinkedHashMap<>();
        for (int i=1;i<16;i++){
            values.put(i,getRandom());
        }
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
        Iterator it=values.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry= (Map.Entry) it.next();
            if ((yRelativeValue -(int)entry.getValue())<currentTouchIndexY&&l<currentTouchIndexX&&r>currentTouchIndexX){
                paint.setColor(0xff8B4513);
                canvas.drawText(entry.getKey()+":"+entry.getValue(),l-15,yRelativeValue -(int)entry.getValue()-30,textPaint);
            }else{
                paint.setColor(colors[(int)(entry.getKey())%4]);
            }
            canvas.drawRect(l
                    , yRelativeValue -(int)entry.getValue()
                    , r
                    , yRelativeValue, paint);
            canvas.drawText(entry.getKey()+"",l,yRelativeValue+35,textPaint);
            l = r + barInterval;
            r = r + barInterval + barWidth;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != mGestureListener){
            return mGestureListener.onTouchEvent(event);
        }
        return true;
    }

    /**
     * 触摸拖动
     *
     */
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            offset_x = offset_x + (int) (distanceX * scroll_rate);
            Log.e("onScroll","x:  "+offset_x);
            // BarChart.this.scrollBy((int) (distanceX * scroll_rate), 0);
            return true;
        }
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return true;
        }
        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int x= (int) e.getRawX();
            int y= (int) e.getY();
            currentTouchIndexY=y;
            int touchX=x+offset_x;//-xRelativeValue; //矫正触摸位置，设置模拟触摸位置相对于第一个柱子的右边位置
            if (touchX-barInterval<barWidth&&touchX-barInterval>0){
                Log.e("touch",1+"");
            }else if (touchX-barInterval>0){
                Log.e("touch",touchX/(barWidth+barInterval)+1+"");
            }
            currentTouchIndexX=touchX;
            invalidate();
            return true;
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
