package com.zxx.lrean.lrean2016.recycierview;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import com.zxx.lrean.lrean2016.banner.ADInfo;
import com.zxx.lrean.lrean2016.modle.newslist.NewsBean;
import com.zxx.lrean.lrean2016.viewpager.BaseAdapter;
import com.zxx.lrean.lrean2016.banner.CycleViewPager;
import com.zxx.lrean.lrean2016.viewpager.Utils;
import com.zxx.lrean.lrean2016.banner.ViewFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */
public abstract class  NewSlidingRecycierViewAdapter extends BaseAdapter {
    private LayoutInflater minflater;
    private Context mContext;
    private List<String> mdata;

    public static final int VIEW_TYPE_LIVE_TAG = 1;//轮播
    public static final int VIEW_TYPE_NEWS = 2;//新闻
    private static final int VIEW_TYPE_LOAD_MORE = 6;//底部自动加载区
    private Context context;

    private List<ADInfo> adInfos = new ArrayList<>();
    private List<Object> newses = new ArrayList<>();
    private ViewPagerHolder viewPagerHolder;


    public NewSlidingRecycierViewAdapter(Context context, List<String> mdata){
        super(context);
        mContext=context;
        this.mdata=mdata;
        minflater=LayoutInflater.from(context);
        //viewPagerHolder=minflater.inflate()
    }

    /**
     * 创建Viewholder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LIVE_TAG) {
            //轮播
            return viewPagerHolder;
        } else if (viewType == VIEW_TYPE_NEWS) {
            //新闻
            View root = LayoutInflater.from(context).inflate(-1, parent, false);
            return new NewsHolder(root);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsHolder) {
            bindNewsData((NewsHolder)holder,position);
        }   else if (holder instanceof LoadMoreViewHolder) {
            //加载数据
            loadMore(newses.size());
        }
    }


    @Override
    public int getItemCount() {
        return mdata.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) { //广告
            return VIEW_TYPE_LIVE_TAG;
        }else if (position == getItemCount() - 1) { //最后一个元素显示加载
            return VIEW_TYPE_LOAD_MORE;
        } else { //新闻
            return VIEW_TYPE_NEWS;
        }
    }

    public  void addData(List<NewsBean> bean){

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 轮播图
     */
    class ViewPagerHolder extends RecyclerView.ViewHolder {
        CycleViewPager pager_ads;
        public ViewPagerHolder(View itemView) {
            super(itemView);
            FragmentActivity activity = (FragmentActivity) context;
            pager_ads = (CycleViewPager) activity.getSupportFragmentManager().findFragmentById(-1);
            // 设置循环，在调用setData方法前调用
            pager_ads.setCycle(true);
            pager_ads.setWheel(true);
            pager_ads.setTime(5000);
            //设置圆点指示图标组居中显示，默认靠右
            pager_ads.setIndicatorCenter();
        }
    }

    /**
     * 新闻列表
     */
    class NewsHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        ImageView img;
        View more;
       // News news;
        public NewsHolder(View itemView) {
            super(itemView);
//            title = (TextView) itemView.findViewById(R.id.news_title);
//            content = (TextView) itemView.findViewById(R.id.news_content);
//            img = (ImageView) itemView.findViewById(R.id.news_img);
//            more = itemView.findViewById(R.id.more);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ActivityLauncher.toBrowserActivity(context, news);
                }
            });
        }
    }
    /**
     * 刷新“广告”的显示数据
     *
     * @param adInfos 刷新的数据
     */
    public void setADInfos(List<ADInfo> adInfos) {
        if (Utils.isEmpty(adInfos)) {
            return;
        }
        this.adInfos.addAll(adInfos);
        List<ImageView> views = generateImageViews(adInfos);
        viewPagerHolder.pager_ads.setData(views, adInfos, imageCycleViewListener);
    }

    /**
     * 初始化广告的操作
     *
     * @param infos
     * @return
     */
    public List<ImageView> generateImageViews(List<ADInfo> infos) {
        List<ImageView> views = new ArrayList<>();
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(context, infos.get(infos.size() - 1).getImageUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(context, infos.get(i).getImageUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(context, infos.get(0).getImageUrl()));
        return views;
    }

    /**
     * 首页轮播图片点击事件
     */
    CycleViewPager.ImageCycleViewListener imageCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(ADInfo info, int postion, View imageView) {
            Log.e("time", info.getImageUrl());
        }
    };

    private void bindNewsData(NewsHolder holder, int position) {
       // News news =newses.get(position-1);
       // assert news != null;
//        if(news.getContent()!=null){
//            holder.content.setText(Html.fromHtml(news.getContent()));
//        }
//        holder.title.setText(news.getTitle());
//        Glide.with(context).load("").centerCrop().placeholder(R.drawable.ic_launcher).into(holder.img);
//        holder.news = news;
    }

    public void addNewses(List<Object> newses) {
        if (Utils.isNotEmpty(newses)) {
            this.newses.addAll(newses);
        }
    }
    public void clearNewses(){
        newses.clear();
    }

    public int livesCount() {
        return newses.size();
    }

}
