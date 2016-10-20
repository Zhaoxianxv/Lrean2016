package com.zxx.lrean.lrean2016.modle.newslist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yfy1 on 2016/8/2.
 */
public class Root implements Parcelable {
    private List<NewsBean> news ;



    public void setNews(List<NewsBean> news){
        this.news = news;
    }

    public List<NewsBean> getNews(){
        return this.news;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    protected Root(Parcel in) {
    }

    public static final Creator<Root> CREATOR = new Creator<Root>() {
        @Override
        public Root createFromParcel(Parcel in) {
            return new Root(in);
        }

        @Override
        public Root[] newArray(int size) {
            return new Root[size];
        }
    };
}
