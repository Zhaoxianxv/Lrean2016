package com.zxx.lrean.lrean2016.viewpager;

/**
 * Created by Aj Liao on 2016/3/4.
 *
 *  处理LoadMore的处理类型
 */
public enum LoadState {
    LOADING("加载..."),
    NORMAL("更多..."),
    NO_MORE("没有了更多数据了"),
    NO_RESULT("暂无数据"),
    NETWORK_ERROR("网络异常"),
    HIDE("");
    public String text;

    LoadState(String text) {
        this.text = text;
    }
}
