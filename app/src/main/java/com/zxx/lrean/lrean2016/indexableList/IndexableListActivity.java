package com.zxx.lrean.lrean2016.indexableList;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import com.zxx.lrean.lrean2016.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 1、初始化 item
 * 2、根据section获取postion
 */

public class IndexableListActivity extends Activity {

    List<String> data ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indexable_list);
        data=new ArrayList<>();
        MyAdapter adapter=new MyAdapter(this,android.R.layout.simple_list_item_1,getData());
        IndexListView list= (IndexListView) findViewById(R.id.list_view);
        list.setAdapter(adapter);
        //一定要设置为true：原因看逻辑
        list.setFastScrollEnabled(true);
    }


    public List<String> getData() {
        //这里一般取（app）数据库的数据
        data.add("A充满风险分担V；h");
        data.add("Hasdasdas充满风；h");
        data.add("Ysadasdah");
        data.add("Radsadasdash");
        data.add("Ehgfsa充满风h");
        data.add("Ughfsadasd充满h");
        data.add("Odfvmljsadasda充满风险");
        data.add("P123dfvmljsadasdasdas充");
        data.add("Ll gmndfvmljsadasdah");
        data.add("Qlgmn7dfvmljsadas");
        data.add("Agmns充满风险h");
        data.add("Ddjsadasdasdas充满h");
        data.add("Gxas充满风险分担V；h");
        data.add("Zsadasdah");
        data.add("Kadsadasdash");
        data.add("Mhgfsa充满风h");
        data.add("Fghfsadasd充满h");
        data.add("Sdfvmljsadasda充满风险");
        data.add("F123dfvmljsadasdasdas充");
        data.add("Hl gmndfvmljsadasdah");
        data.add("Plgmn7dfvmljsadas");
        data.add("Agmns充满风险h");
        data.add("Jdjsadasdasdas充满h");
        data.add("Fdgmn7dfvmljs");

        //为data排序
        Collections.sort(data);

        return data;
    }

    /** SectionIndexer有三个抽象方法实现分别是：
     * 1、getSections()       获得一个对象数组
     * 2、getPositionForSection(int sectionIndex)   根据Section得到position
     * 3、getSectionForPosition(int position)        根据position得到Section
     *
     */
    class MyAdapter extends ArrayAdapter<String> implements SectionIndexer {


        private  String mSection="#ABCDEFGHIJKLMNOPQRSTUVWXYZ*";
        public MyAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        /**
         *
         * @return
         */
        @Override
        public Object[] getSections() {
            String[] object=new String[mSection.length()];
            //将每个section作为单个元素放到sections中
            for (int i=0;i<mSection.length();i++){
                object[i]=String.valueOf(mSection.charAt(i));
            }
            return object;
        }

        @Override
        public int getPositionForSection(int sectionIndex) {
            //从当前sectionIndex向前查；如果没有向前查直到#结束
            for (int i=sectionIndex;i>0;i--){
                //第二层查找
                for(int j=0;j<getCount();j++){

                    if (i==0){
                        //查询数字
                        for(int k=0;k<=9;k++){
                            if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(k))){
                                return j;
                            }
                        }
                    }else{
                        //查询字母
                        //match(,)
                        if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(mSection.charAt(i)))){
                            return j;
                        }
                    }
                }
            }
            return 0;
        }

        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }

    }
}

