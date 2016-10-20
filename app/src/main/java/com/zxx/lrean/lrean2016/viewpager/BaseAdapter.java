package com.zxx.lrean.lrean2016.viewpager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxx.lrean.lrean2016.R;


/**
 * Created by Aj Liao on 2016/1/14.
 * <p/>
 * 与RecyclerView.Adapter不同的是，BaseRecyclerViewAdapter封装了LoadMoreViewHolder
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final static int FIRST_PAGE = 0;//常量：第一页
    protected LoadMoreViewHolder loadMoreViewHolder;
    protected OnLoadMoreListener onLoadMoreListener;

    public BaseAdapter(Context context) {
        loadMoreViewHolder = new LoadMoreViewHolder(View.inflate(context, R.layout.view_load_more, null));


    }


    /**
     * “自动加载”ViewHolder
     */
    public static class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pb_progress;
        TextView tv_text;
        LoadState state = LoadState.NORMAL;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            pb_progress = (ProgressBar) itemView.findViewById(R.id.load_more_progress);
            tv_text = (TextView) itemView.findViewById(R.id.load_more_text);
        }

        public void setLoadState(LoadState state) {
            this.state = state;

            //修改界面
            if (state == LoadState.NORMAL) {
                tv_text.setVisibility(View.VISIBLE);
                pb_progress.setVisibility(View.GONE);
            } else if (state == LoadState.LOADING) {
                tv_text.setVisibility(View.VISIBLE);
                pb_progress.setVisibility(View.VISIBLE);
            } else if (state == LoadState.NO_MORE) {
                tv_text.setVisibility(View.VISIBLE);
                pb_progress.setVisibility(View.GONE);
            } else if (state == LoadState.NO_RESULT) {
                tv_text.setVisibility(View.VISIBLE);
                pb_progress.setVisibility(View.GONE);
            } else if (state == LoadState.NETWORK_ERROR) {
                pb_progress.setVisibility(View.GONE);
            } else if (state == LoadState.HIDE) {
                tv_text.setVisibility(View.GONE);
                pb_progress.setVisibility(View.GONE);
            }
            tv_text.setText(state.text);
        }

        public LoadState getLoadState() {
            return state;
        }
    }

    public static interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoadState(LoadState state) {
        loadMoreViewHolder.setLoadState(state);
    }

    /**
     * 处理LoadMoreViewHolder的逻辑
     */
    public void loadMore(int currListSize) {
        if (loadable(currListSize)) {//如果满足Load的条件
            loadMoreViewHolder.setLoadState(LoadState.LOADING);
            onLoadMoreListener.onLoadMore();
        }
    }
    /**
     * 是否满足触发load的条件
     */
    public boolean loadable(int currListSize) {
        return currListSize > 0 //当前有数据
                && currListSize % Constants.PAGE_SIZE == 0//当前显示的是整页
                && Utils.isNotNull(onLoadMoreListener)//监听器不能为空
                && loadMoreViewHolder.getLoadState() != LoadState.LOADING;//现在没有Loading;
    }
}