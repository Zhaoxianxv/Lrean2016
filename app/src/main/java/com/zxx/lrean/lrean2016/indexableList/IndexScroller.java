package com.zxx.lrean.lrean2016.indexableList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Adapter;

import android.widget.ListView;
import android.widget.SectionIndexer;


import com.zxx.lrean.lrean2016.sliding.LogShow;

import java.util.logging.LogRecord;

/**
 * 负责绘制索引条
 * Created by Administrator on 2016/4/27.
 */
public class IndexScroller {
    //索引的宽度
    private float mIndexbarWidth;
    //索引条到右侧边缘的距离
    private float mIndexbarMargin;
    //在中心显示的预览文本到四周的距离
    private float mPreviewPadding;
    //频幕密度值 dp sp
    private float mDensity;
    //当前
    private float mScaledDensity;
    //设置一个透明度
    private float mAlphaRate;
    //索引条的状态
    private int mState=STATE_HIDDEN;

    private int mListViewWidth;
    private int mListViewHeight;
    private int mCurrentSection=-1;
    private boolean mIsIndexing=false;
    private ListView mListView=null;
    private SectionIndexer mIndexer=null;
    private String[] mScetions=null;
    //
    private RectF mIndexbarRect;
    //索引条的四个状态STATE_SHOWN:显示，STATE_HIDDEN:隐藏
    private static final int STATE_HIDDEN=0;
    private static final int STATE_SHOWING=1;
    private static final int STATE_SHOWN=2;
    private static final int STATE_HIDING=3;

    public void  setmState(int state) {
        if (state<STATE_HIDDEN||state>STATE_HIDING)
            return;
        mState = state;
        switch (mState){
            case STATE_HIDDEN:
                mHandler.removeMessages(0);
                break;
            case STATE_SHOWING:
                mAlphaRate=0;
                fade(10);
                break;
            case STATE_SHOWN:
                mHandler.removeMessages(0);
                break;
            case STATE_HIDING:
                mAlphaRate=1;
                fade(10);
                break;
        }
    }

    public IndexScroller(Context context,ListView lv){
        //获取屏幕密度的比值
        mDensity=context.getResources().getDisplayMetrics().density;
        //字体有关
        mScaledDensity=context.getResources().getDisplayMetrics().scaledDensity;
        mListView=lv;
        setAdapter(mListView.getAdapter());
        //根据屏幕密度技术设置索引条的宽度
        mIndexbarWidth=20*mDensity;
        mIndexbarMargin=10*mDensity;
        mPreviewPadding=5*mDensity;
    }
    public void  setAdapter(Adapter adapter){
        //接口
        if (adapter instanceof SectionIndexer){
            mIndexer=(SectionIndexer)adapter;
            mScetions=(String[])mIndexer.getSections();
        }
    }

    /**
     * 绘制索引条和预览文本
     * 1、绘制索引条，包括索引条的背景和文本
     * 2、绘制预览文本和背景
     */
    public  void draw(Canvas canvas){
        //如果为隐藏就不绘制了
        if (mState==STATE_HIDDEN){
            return;
        }
        //设置索引条的绘制属性
        Paint indexbarpaint=new Paint();
        indexbarpaint.setColor(Color.BLACK);
        indexbarpaint.setAlpha((int) (64 * mAlphaRate));
        //绘制索引条(矩形区域)
        canvas.drawRoundRect(mIndexbarRect,5*mDensity,5*mDensity,indexbarpaint);
        if (mScetions!=null&&mScetions.length>0){
            if (mCurrentSection>=0){
                Paint previewPaint=new Paint();
                previewPaint.setColor(Color.BLACK);
                previewPaint.setAlpha(96);
                Paint previewTextpaint=new Paint();
                previewTextpaint.setColor(Color.WHITE);
                previewTextpaint.setTextSize(50 * mScaledDensity);
                // 让 RectF 的文本的x轴在中心位置
                previewTextpaint.setTextAlign(Paint.Align.CENTER);
                float previewTextWidth=previewTextpaint.measureText(mScetions[mCurrentSection]);

                float previewSize=2*mPreviewPadding+previewPaint.descent()-previewTextpaint.ascent();
                //预览文本背景区
                RectF previewRect=new RectF(
                        (mListViewWidth-previewSize)/2,
                        (mListViewHeight-previewSize)/2,
                        (mListViewWidth-previewSize)/2+previewSize,
                        (mListViewHeight-previewSize)/2+previewSize
                        );
                //绘制背景
                canvas.drawRoundRect(previewRect,5*mDensity,5*mDensity,previewPaint);
                //绘制预览文本
                LogShow.log_show_e("索引条--7");
                canvas.drawText(
                        mScetions[mCurrentSection], (previewRect.left+previewRect.right)/2,
                        previewRect.top+mPreviewPadding-previewTextpaint.ascent()+1,
                        previewTextpaint);
            }
        }
        //设置索引的属性
        Paint indexPaint=new Paint();
        indexPaint.setColor(Color.WHITE);
        indexPaint.setAlpha((int)(255*mAlphaRate));
        indexPaint.setTextSize(12*mScaledDensity);
        float sectionHeight=(mIndexbarRect.height()-2*mIndexbarMargin)/mScetions.length;
        float paddingTop=(sectionHeight-(indexPaint.descent()-indexPaint.ascent()))/2;
        for (int i=0;i<mScetions.length;i++){
            float paddingLeft=(mIndexbarWidth-indexPaint.measureText(mScetions[i]))/2;
            canvas.drawText(
                    mScetions[i],
                    mIndexbarRect.left+paddingLeft,
                    mIndexbarRect.top+mIndexbarMargin+sectionHeight*i+paddingTop-indexPaint.ascent(),
                    indexPaint);
        }
    }
    /**
     * 当Size发生改变时调用
     */
    public void onSizeChanged(int w,int h,int oldw,int oldh){
        mListViewWidth=w;
        mListViewHeight=h;
        mIndexbarRect=new RectF(w-mIndexbarMargin-mIndexbarWidth, mIndexbarMargin, w-mIndexbarMargin, h-mIndexbarMargin);
    }
    private void fade(long delay){
        //有可能没有执行完，所以先清理消息
        mHandler.removeMessages(0);
        //延迟发送.sendEmptyMessageAtTime；.sendMessageDelayed(msg, 1000)
        mHandler.sendEmptyMessageAtTime(0, SystemClock.uptimeMillis()+delay);

    }
    //在界面显示
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (mState){
                case STATE_HIDDEN:
                    mHandler.removeMessages(0);
                    break;
                case STATE_SHOWING:
                    //渐变显示
                    mAlphaRate+=(1-mAlphaRate)*0.2;
                    if (mAlphaRate>0.9){
                        mAlphaRate=1;
                        setmState(STATE_SHOWN);
                    }
                    mListView.invalidate();
                    fade(10);
                    break;
                case STATE_SHOWN:
                    setmState(STATE_HIDING);
                    fade(3000);
                    break;
                case STATE_HIDING:
                    //渐变消失
                    mAlphaRate-=mAlphaRate*0.2;
                    if (mAlphaRate<0.1){
                        mAlphaRate=0;
                        setmState(STATE_HIDDEN);
                    }
                    mListView.invalidate();
                    fade(30);
                    break;
            }
        }
    };


    //管理触摸条

    public boolean onTouchEvent(MotionEvent ev) {
       switch (ev.getAction()){
           case MotionEvent.ACTION_DOWN:
               if(mState!=STATE_HIDDEN&& contains(ev.getX(),ev.getY()))
//               if(mState!=STATE_HIDDEN)
               {
                   setmState(STATE_SHOWN);
                   mIsIndexing=true;
                   //通过触摸点获取当前的Section的索引
                   mCurrentSection=getSectionByPoint(ev.getY());
                   //将listView定位到指定的item上
                   mListView.setSelection(mIndexer.getPositionForSection(mCurrentSection));
                   return true;
               }
               break;
           case MotionEvent.ACTION_MOVE:
               //滑动显示索引条
               if (mIsIndexing){
                   if(contains(ev.getX(),ev.getY()))
                   {
                       mCurrentSection=getSectionByPoint(ev.getY());
                       mListView.setSelection(mIndexer.getPositionForSection(mCurrentSection));

                       Log.e("索引条","----------");

                   }
                   return true;
               }
               break;
           case MotionEvent.ACTION_UP:
               if (mIsIndexing){
                   mIsIndexing=false;
                   mCurrentSection=-1;
               }
               if (mState==STATE_SHOWN){
                   setmState(STATE_SHOWN);
               }
               break;
       }
        return false;
    }

    private int getSectionByPoint(float y) {
        return (int)((y-mIndexbarRect.top)/(mIndexbarRect.height()/mScetions.length));
    }

    //判断事件是否在索引条上
    private boolean contains(float x, float y) {

        return (x>=mIndexbarRect.left && y>=mIndexbarRect.top &&
                y<=mIndexbarRect.top+mIndexbarRect.height() && x<=mIndexbarRect.left+mIndexbarRect.width());
    }
    //显示
    public void show() {
        LogShow.log_show_e("显示——"+mState);
        if (mState==STATE_HIDDEN){
            setmState(STATE_SHOWING);

        }else if (mState==STATE_HIDING){
            setmState(STATE_SHOWING);
        }

    }

    //隐藏
    public void hide() {
        LogShow.log_show_e("隐藏3——"+mState);
        if (mState==STATE_SHOWING ){
            setmState(STATE_HIDING);
        }else if (mState==STATE_SHOWN){
            setmState(STATE_HIDDEN);
        }

    }
}
