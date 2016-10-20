package com.zxx.lrean.lrean2016.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/16.
 * 重写了setContentView()方法，并在里面添加ButterKnife,这样就不用在每个子Activity里调用ButterKnife.bind(this)了
 */
public class BaseActivity extends AppCompatActivity {

    protected BaseActivity mActivity;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //初始化 ButterKnife
        ButterKnife.bind(this);
    }
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        //初始化 ButterKnife
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    /**
     * 尽量使用onPageBack()方法来销毁页面，不要直接调用finish()，
     * 这种方式的好处有2种：1、方便添加退出动画。2、方便做Umeng统计。
     */
    public void onPageBack() {
        finish();
    }


    public void closeKeyWord() {
        /** 隐藏软键盘 **/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputManger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyWord() {
        /** 弹出软键盘 **/
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 全局Toast
     */
    Toast toast;


}
