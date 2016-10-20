package com.zxx.lrean.lrean2016.quick_mark;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CaptureRequest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;
import com.zxx.lrean.lrean2016.R;
import com.zxx.lrean.lrean2016.base.BaseActivity;
import com.zxx.lrean.lrean2016.sliding.LogShow;

import java.util.logging.Logger;

import butterknife.Bind;

import butterknife.OnClick;


public class QuickMarkActivity extends BaseActivity {
    Activity mActivity=this;

    @Bind(R.id.q_m_long_end)
    TextView text_end;
    @Bind(R.id.q_r_maker_edit)
    EditText maker_string;
    @Bind(R.id.qr_icon)
    ImageView icon;
    @Bind(R.id.qr_loge_checkbox)
    CheckBox box;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_quick_mark);

     }
    //扫描执行
    public void scan(View v){
        startActivityForResult(new Intent(mActivity, CaptureActivity.class), 0);
    }
    //生成二维码
    public void make(CheckBox box){
        String q_r_text=maker_string.getText().toString();
        if (q_r_text.equals("")){
            Toast.makeText(mActivity,"输入类容不能为空",Toast.LENGTH_LONG).show();
            return;
        }else {
            //createQRCode(q_r_text,500,500,null);
            //1,生成的Sting；2、3、宽和高；4、log要
           //98ji Bitmap bitmap= EncodingUtils.createQRCode(q_r_text,800,800,box.isChecked() ? BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher) : null);
            Bitmap bitmap=null;
            if (box.isChecked()){
                bitmap= EncodingUtils.createQRCode(q_r_text,800,800, BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher));
            }else {
                bitmap= EncodingUtils.createQRCode(q_r_text,800,800,null);
            }
            icon.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            String result=bundle.getString("result");
            text_end.setText(result);
        }
    }

    @OnClick(R.id.q_m_long)
     void setQ_m_long(View v){
        scan(v);
    }
    @OnClick(R.id.q_r_maker_text)
    void setText_maker(){
       make(box);
    }

    @OnClick(R.id.q_r_clen_text)
    void setClear(){
        icon.setImageBitmap(null);
    }


 }
