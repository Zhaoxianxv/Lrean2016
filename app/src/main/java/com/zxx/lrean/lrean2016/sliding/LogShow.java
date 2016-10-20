package com.zxx.lrean.lrean2016.sliding;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/11.
 */
public class LogShow {
    public static void  log_show_e(String s){
        Log.e("zxxtt","------"+s);
    }
    public static void  toast(Context con,String s){
       Toast.makeText(con,"zxx"+s,Toast.LENGTH_LONG).show();
    }

}
