package com.app.fan.idcardscanproject.util;


import android.app.Activity;
import android.widget.ImageView;

import com.app.fan.idcardscanproject.R;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by Administrator on 2016/10/19.
 */

public class MyImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(new File(path))
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }

}