package com.zxx.lrean.lrean2016.indexableList;

import android.content.Context;

import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zxx.lrean.lrean2016.sliding.LogShow;

/**
 * 封装了索引条及其功能
 * Created by Administrator on 2016/4/27.
 */
public class IndexListView  extends ListView{

    private  boolean mIsFastScrollabled=false;
    //负责绘制索引引条
    private IndexScroller mScroller;
    private GestureDetector mGestureDetector;
    Context context;

    public IndexListView(Context context) {
        super(context);
    }

    public IndexListView(Context context, AttributeSet attrs) {
        super(context, attrs);
       this.context=context;
    }

    public IndexListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 判断是否快速滑动
     * @return
     */
    @Override
    public boolean isFastScrollEnabled() {
        return mIsFastScrollabled;
    }

    @Override
    public void setFastScrollEnabled(boolean enabled) {
        super.setFastScrollEnabled(enabled);
        mIsFastScrollabled=enabled;
        if (mIsFastScrollabled){
            if (mScroller==null){
                mScroller =new IndexScroller(context,this);
            }
            mScroller.show();
        }else{
            //没有快速滑动
            if (mScroller!=null){
                mScroller.hide();//隐藏
                mScroller=null;
            }
        }
    }
    //用于绘制右侧的索引条

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);//绘制以前的东西
        if (mScroller!=null){
            mScroller.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        //如果mScroller自己来处理触摸事件，该方法返回true
        if (mScroller!=null&&mScroller.onTouchEvent(ev)){
            return true;
        }
        if (mGestureDetector==null){
       //使用手势处理触摸事件
            mGestureDetector=new GestureDetector(getContext(),
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            //显示索引条
                            LogShow.log_show_e("手势1");
                            mScroller.show();
                            return super.onFling(e1, e2, velocityX, velocityY);
                        }
                    });
        }
        mGestureDetector.onTouchEvent(ev);

        return super.onTouchEvent(ev);
    }
    //重写setAdapter方法
    public void setAdapter(ListAdapter adapter){
        super.setAdapter(adapter);
        if (mScroller!=null){
            mScroller.setAdapter(adapter);

        }
    }

    /**
     * 当尺寸改变时
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mScroller!=null){
            mScroller.onSizeChanged(w, h, oldw,oldh);

        }
    }
}
