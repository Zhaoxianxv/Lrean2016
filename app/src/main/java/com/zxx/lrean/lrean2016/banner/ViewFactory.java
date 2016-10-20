package com.zxx.lrean.lrean2016.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zxx.lrean.lrean2016.R;


/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		Glide.with(context).load(url)
				.centerCrop()
				.placeholder(R.drawable.ic_launcher)
				.crossFade()
				.into(imageView);
		return imageView;
	}
}
