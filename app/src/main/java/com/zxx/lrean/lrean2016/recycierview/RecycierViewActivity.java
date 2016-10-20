package com.zxx.lrean.lrean2016.recycierview;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.zxx.lrean.lrean2016.R;
import com.zxx.lrean.lrean2016.base.BaseActivity;
import com.zxx.lrean.lrean2016.modle.Infter;
import com.zxx.lrean.lrean2016.modle.RootInfter;


import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class RecycierViewActivity extends BaseActivity {
    @Bind(R.id.recycler_viewPager)
    ViewPager viewPager;
    @Bind(R.id.recycler_tablayout)
    TabLayout tablayout;
    public static final String URL = "http://apptest.yfyit.com/Service1.svc/";
    public static final String NAMESPACE = "http://tempuri.org/";
    public static final String SOAP_ACTION = "http://tempuri.org/IService1/";
    public static final int TIME_OUT = 10000;
    public final static String WCF_TXT = "wcf.txt";
    final static int BUFFER_SIZE = 4096;

    Activity mActivity;
    MyAsyncTaskUpdata mtask;
    List<Fragment> fragments;
    List<String> titles;

    Handler handler=new Handler();
    TabFragmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycier_view);
        mActivity=this;
        fragments= new ArrayList<Fragment>();
        titles=new ArrayList<>();
        initToolbar();
    }
    private void initToolbar() {
        mtask=new MyAsyncTaskUpdata();
        mtask.execute();

        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色

//
    }

    /**
     * 手机返回时会执行onpause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //判断AsyncTask不为null且Status.RUNNING在运行状态
        if (mtask!=null&&mtask.getStatus()== AsyncTask.Status.RUNNING) {
            //为mtask标记cancel(true)状态
            mtask.cancel(true);
        }

    }
    private void initGson(String hh) {
        Gson gson=new Gson();
        RootInfter root=gson.fromJson(hh,RootInfter.class);
        List<Infter> titleData=new ArrayList<Infter>() ;
        titleData=root.getWebsitemenu();
        for (int i=0;i<root.getWebsitemenu().size();i++){
            Infter infter=new Infter();
            String name=titleData.get(i).getPrograma_id();
            titles.add(titleData.get(i).getPrograma_name());
            Fragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            //绑定一个id
            bundle.putString("text",titleData.get(i).getPrograma_id());
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        //发送一个消息；
        handler.post(new Runnable() {
            @Override
            public void run() {
                tablayout.setTabTextColors(Color.BLUE, Color.WHITE);
                adapter=new TabFragmentAdapter(fragments, titles,getSupportFragmentManager(),mActivity );
                tablayout.setupWithViewPager(viewPager);
                viewPager.setAdapter(adapter);
//                viewPager .setOffscreenPageLimit(fragments.size());
            }
        });
    }

    public static String InputStreamTOString(InputStream in) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return new String(outStream.toByteArray(),"ISO-8859-1");
    }

    /**
     * 异步任务
     */
    public class MyAsyncTaskUpdata extends AsyncTask<Object[], Integer, Void>{

        @Override
        protected Void doInBackground(Object[]... objects) {
            String jsonobj= null;
            Object[] dd=new Object[]{"02"};
            try {
                InputStream is= getAssets().open(WCF_TXT);
                SoapAccessor soap=SoapAccessor.getInstance();
                soap.init(new SoapAccessor.WcfConfiguration(NAMESPACE, URL, SOAP_ACTION, TIME_OUT, InputStreamTOString(is)));
                jsonobj = soap.LoadResult(dd,"getmenu");
                String hh=jsonobj.replaceAll("null", "\"\"");
                initGson(hh);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("zxx", "item");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }
        protected void onProgressUpdate(Integer... integers) {
            super.onProgressUpdate(integers);
            //mtask标记cancel(true)不做更新处理
            if (isCancelled()) {
                return;
            }
        }
    }

}
