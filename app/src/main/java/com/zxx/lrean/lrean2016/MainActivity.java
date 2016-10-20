package com.zxx.lrean.lrean2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zxx.lrean.lrean2016.banner.BannerActivity;
import com.zxx.lrean.lrean2016.index.IndexActivity;
import com.zxx.lrean.lrean2016.indexableList.IndexableListActivity;

import com.zxx.lrean.lrean2016.quick_mark.QuickMarkActivity;
import com.zxx.lrean.lrean2016.recycierview.RecycierViewActivity;
import com.zxx.lrean.lrean2016.sliding.SlidingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private  List<Map<String,Object>> activitys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list= (ListView) findViewById(R.id.index_activity_list);
        SimpleAdapter adapter=new SimpleAdapter(this,getData(),
                R.layout.layout_simple,new String[]{"title"},new int[]{R.id.simple_text});
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity((Intent) activitys.get(position).get("intent"));
            }
        });

    }
    public List<Map<String,Object>> getData(){
        activitys= new ArrayList<>();

        addItem(activitys, "fragment的使用", IndexActivity.class);
        addItem(activitys, "pagerView 和标题关联", SlidingActivity.class);
        addItem(activitys, "索引条，索引控件(类似联系人)", IndexableListActivity.class);
        addItem(activitys, "二维码扫描(QuickMark)", QuickMarkActivity.class);
        addItem(activitys, "recycierview的使用", RecycierViewActivity.class);
        addItem(activitys, "banner的使用", BannerActivity.class);
//        addItem(activitys, "viewpager+fragment+recyclerView的使用", DomePagerActivity.class);

        return activitys;
    }

    public void addItem(List<Map<String,Object>> activitys,String text,Class<?> obj) {
        addItem(activitys, text, new Intent(this,obj));

    }
    protected void addItem(List<Map<String, Object>> data, String name,
                           Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

}
