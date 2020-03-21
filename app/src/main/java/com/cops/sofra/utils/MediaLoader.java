package com.cops.sofra.utils;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

public class MediaLoader implements AlbumLoader {
    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(imageView);
    }

}
