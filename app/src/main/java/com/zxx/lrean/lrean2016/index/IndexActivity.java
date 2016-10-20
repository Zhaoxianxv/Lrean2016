package com.zxx.lrean.lrean2016.index;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zxx.lrean.lrean2016.R;



public class IndexActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,TradeFragment.newInstance()).commit();
    }

}
