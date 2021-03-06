package com.zxx.lrean.lrean2016.recycierview;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter {

    private List<String> titles;
    private Context context;
    private List<Fragment> fragments;
    FragmentManager fm;

    public void setData(List<Fragment> fragments,List<String> titles){
        this.fragments = fragments;
        this.titles = titles;
        notifyDataSetChanged();
    }

    public TabFragmentAdapter(List<Fragment> fragments, List<String> titles, FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.titles = titles;
        Log.e("zxx", "titles.size()------" +titles.size());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return 1;
//        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
//    @Override
//    public Fragment instantiateItem(ViewGroup container, int position) {
//        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//        fm.beginTransaction().show(fragment).commit();
//        return fragment;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//           // super.destroyItem(container, position, object);
//        Fragment fragment = fragments.get(position);
//        fm.beginTransaction().hide(fragment).commit();
//    }

}

