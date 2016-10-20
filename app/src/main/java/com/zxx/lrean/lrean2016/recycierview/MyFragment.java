package com.zxx.lrean.lrean2016.recycierview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zxx.lrean.lrean2016.MainActivity;
import com.zxx.lrean.lrean2016.R;
import com.zxx.lrean.lrean2016.banner.ADInfo;
import com.zxx.lrean.lrean2016.modle.newslist.NewsBean;
import com.zxx.lrean.lrean2016.modle.newslist.Root;
import com.zxx.lrean.lrean2016.viewpager.BaseAdapter;
import com.zxx.lrean.lrean2016.viewpager.Constants;
import com.zxx.lrean.lrean2016.viewpager.LoadState;
import com.zxx.lrean.lrean2016.viewpager.RecycleViewDivider;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/29.
 */
public class MyFragment extends Fragment {

    protected final static int FIRST_PAGE =0;//常量：第一页

    //识别是否加载导数据
    boolean updata;

    //异步请求页码
    int page=FIRST_PAGE;
    private String mText;
    private List<NewsBean>   bean=new ArrayList<>();

    private List<ADInfo> infos = new ArrayList<ADInfo>();
    Root listBean;
    FAsyncTaskUpdata task;
    NewSlidingRecycierViewAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;
    View mFragmentView;
    Context mactivity;

    private boolean mHasLoadedOnce = false;// 页面已经加载过
    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        if(getArguments()!=null){
            mText = getArguments().getString("text");
        }
        mactivity=getContext();

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recycierview,container,false);
        intView(view);
        return view;
    }

    //初始化页面
    public void intView(View view){
        adapter=new NewSlidingRecycierViewAdapter(mactivity,bean);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == adapter.getItemCount() - 1) { //加载
                    return 1;//跨1列
                } else if (position < 1) {
                    return 1;//跨1列
                } else { //新闻列表
                    return 1;//跨1列
                }
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.news_recycierview);
        swipeRefresh= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        //为recycierview添加布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mactivity, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.gray)));

        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new BaseAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (swipeRefresh.isRefreshing())
                    return;
                Log.e("zxx", "------刷新oonLoadMore())");
                loadData();
            }
        });
        initSwipeRefresh();
        adapter.notifyDataSetChanged();
    }

    /**
     * 刷新监听
     */
    private void initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                page=FIRST_PAGE;
                Log.e("zxx","------刷新onRefresh())--page---"+page);
                loadData();
            }
        });
        swipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (swipeRefresh.isRefreshing())
                    return;
                Log.e("zxx","------刷新run()");
                swipeRefresh.setRefreshing(true);
                loadData();

            }
        }, 50L);

    }


    //取出数据
//    @Override
//    protected void onRestoreState(Bundle savedInstanceState) {
//        super.onRestoreState(savedInstanceState);
//        listBean=savedInstanceState.getParcelable(mText);
//        bean=listBean.getNews();
//        adapter.setNewses(bean);
//    }
//    //cunru数据
//    @Override
//    protected void onSaveState(Bundle outState) {
//        super.onSaveState(outState);
//        outState.putParcelable(mText,listBean);
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        bean=null;
        listBean=null;
//        task=null;
    }


    /**
     * 加载数据操作
     */
    private void loadData() {


        FAsyncTaskUpdata more=new FAsyncTaskUpdata();
        more.execute(page);
//        Toast.makeText(getActivity(),"s刷新了"+mText,Toast.LENGTH_LONG).show();

    }




    /**
     * 解析数据
     */
    private boolean getNewsOfNet(String hh) {

        bean = null;
        Gson gson = new Gson();
        listBean = gson.fromJson(hh, Root.class);
        bean = listBean.getNews();
        Log.e("zxx", "bean.size()-----" + bean.size());
        if (bean.size() == 0||bean.size() < Constants.PAGE_SIZE) {
            return false;
        }
        if (page == FIRST_PAGE) {
            //如果是第一页
            adapter.clearNewses();
        }

        return true;

    }
    /**
     * 获取banner数据类型模型
     *
     */
    private void getBanner(List<NewsBean> bean) {

        List<ADInfo> banner=new ArrayList<>();
        for (int i=0;i<4;i++){
            ADInfo adinfo=new ADInfo();
            adinfo.setImageUrl(bean.get(i).getNewslist_image());
            adinfo.setTitle("" + bean.get(i).getNewslist_image());
            banner.add(adinfo);
            Log.e("zxx","构建数据模型adinfo.getImageUrl()----"+adinfo.getImageUrl()+"----"+banner.size());
        }
        BannerData(banner);
    }

    private void BannerData(List<ADInfo> data) {
        Log.e("zxx","刷新“广告”的显示数据"+data.get(0).getImageUrl());
        adapter.setADInfos(data);
    }
    /**
     * 转换流方法
     * @param in
     * @return
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[RecycierViewActivity.BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,RecycierViewActivity.BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return new String(outStream.toByteArray(),"ISO-8859-1");
    }

    /**
     * 异步任务
     */
    public class FAsyncTaskUpdata extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.setLoadState(LoadState.LOADING);

        }

        @Override
        protected Void doInBackground(Integer... objects) {
            //mtask标记cancel(true)不做更新处理
            int async_page=objects[0];
            Object[] dd=new Object[]{mText,async_page, Constants.PAGE_SIZE,""};
            if (isCancelled()) {
                return null;
            }
            String jsonobj= null;

            try {
                InputStream is= getActivity().getAssets().open(RecycierViewActivity.WCF_TXT);
                SoapAccessor soap=SoapAccessor.getInstance();
                soap.init(new SoapAccessor.WcfConfiguration(RecycierViewActivity.NAMESPACE, RecycierViewActivity.URL, RecycierViewActivity.SOAP_ACTION, RecycierViewActivity.TIME_OUT, InputStreamTOString(is)));
                jsonobj = soap.LoadResult(dd,"getnewslist");
                String hh=jsonobj.replaceAll("null", "\"\"");
                updata=getNewsOfNet(hh);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.e("zxx","updata)----"+updata);
            if (!updata){
                adapter.setLoadState(LoadState.NO_MORE);
                Toast.makeText(mactivity, "没有更多的数据了", Toast.LENGTH_LONG).show();
            }else{
                adapter.setLoadState(LoadState.NORMAL);
                getBanner(bean);
            }
            adapter.addData(bean);

            page++;
            Log.e("zxx", "page-----" + page);
            adapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
            //判断AsyncTask不为null且Status.RUNNING在运行状态
            if (task!=null&&task.getStatus()== AsyncTask.Status.RUNNING) {
                //为mtask标记cancel(true)状态
                task.cancel(true);
            }
        }
        protected void onProgressUpdate(Integer... integers) {
            super.onProgressUpdate(integers);

        }
    }
}
