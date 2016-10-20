package com.zxx.lrean.lrean2016.viewpager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zxx.lrean.lrean2016.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Daniel on 2016/3/8.
 * <p/>
 * 重写了一个抽象的 onCreateView(Bundle savedInstanceState);
 */
public abstract class BaseFragment extends Fragment {

    protected final static int FIRST_PAGE = 1;//常量：第一页
    protected ProgressDialog dialog = null;
    protected View contentView = null;

    protected LayoutInflater inflater = null;
    protected ViewGroup container = null;
    protected BaseActivity mActivity;

//    @Nullable
//    @Bind(R.id.sq_toolbar)
//    protected SQToolBar toolbar;

    /**
     * 重写onCreate，从新定义Activity的初始时的生命周期
     * <p/>
     * 包括：初始化变量-> 初始化控件-> 加载数据
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.inflater = inflater;
        this.container = container;
        this.mActivity = (BaseActivity) getActivity();
//        BusProvider.getBusInstance().register(this);
        onCreateView(savedInstanceState);
        return contentView;
    }

    public abstract void onCreateView(Bundle savedInstanceState);

    /**
     * 显示一个ProgressDialog
     */
    protected void showProgressDialog(String title, String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
        }
        dialog.setCancelable(false);
        if (title != null && !title.equals("")) {
            dialog.setTitle(title);
        }
        if (message != null && !message.equals("")) {
            dialog.setMessage(message);
        }
        dialog.show();
    }

    protected void showProgressDialog(String message) {
        showProgressDialog(null, message);
    }

    /**
     * 隐藏一个ProgressDialog
     */
    protected void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void setContentView(@LayoutRes int layoutResID) {
        contentView = inflater.from(getActivity()).inflate(layoutResID, container, false);
        //初始化 ButterKnife
        ButterKnife.bind(this, contentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
//        BusProvider.getBusInstance().unregister(this);
    }

    /**
     * 全局Toast
     */
    Toast toast;
    protected void toast(String text) {
        if (Utils.isNull(toast)) {
            toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    protected void toast(@StringRes int resId) {
        toast(getString(resId));
    }


    protected int getFlag() {
        return this.hashCode();
    }


}
