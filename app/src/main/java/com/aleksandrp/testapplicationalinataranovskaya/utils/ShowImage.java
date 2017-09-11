package com.aleksandrp.testapplicationalinataranovskaya.utils;

import android.widget.ImageView;

import com.aleksandrp.testapplicationalinataranovskaya.App;
import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.bumptech.glide.Glide;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class ShowImage {

    public static void showImageFromFile(String path, ImageView toImage) {
        Glide.with(App.getContext())
                .load(path)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(toImage);
    }

}
