package com.zxx.lrean.lrean2016.modle.newslist;

import java.io.Serializable;

/**
 * Created by yfy1 on 2016/8/2.
 */
public class NewsBean implements Serializable {
    //图片url
    private String newslist_image;
    //新闻头
    private String newslist_head;
    //新闻类容
    private String newslist_point;
    //新闻时间
    private String newslist_time;
    //新闻点击链接
    private String news_info_detailed;

    private String newsComment_size;

    public void setNewslist_image(String newslist_image){
        this.newslist_image = newslist_image;
    }
    public String getNewslist_image(){
        return this.newslist_image;
    }
    public void setNewslist_head(String newslist_head){
        this.newslist_head = newslist_head;
    }
    public String getNewslist_head(){
        return this.newslist_head;
    }
    public void setNewslist_point(String newslist_point){
        this.newslist_point = newslist_point;
    }
    public String getNewslist_point(){
        return this.newslist_point;
    }
    public void setNewslist_time(String newslist_time){
        this.newslist_time = newslist_time;
    }
    public String getNewslist_time(){
        return this.newslist_time;
    }
    public void setNews_info_detailed(String news_info_detailed){
        this.news_info_detailed = news_info_detailed;
    }
    public String getNews_info_detailed(){
        return this.news_info_detailed;
    }
    public void setNewsComment_size(String newsComment_size){
        this.newsComment_size = newsComment_size;
    }
    public String getNewsComment_size(){
        return this.newsComment_size;
    }

}
