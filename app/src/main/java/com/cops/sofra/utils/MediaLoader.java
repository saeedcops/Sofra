package com.cops.sofra.utils;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import com.cops.sofra.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;
import java.util.Locale;


public class MediaLoader implements AlbumLoader {
    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
//                .error(R.drawable.ic_person_add)
//                .placeholder(R.drawable.ic_person_add)
                .crossFade()
                .into(imageView);
    }

    public static void selectImage(final Activity activity,final ImageView imageView,Action<ArrayList<AlbumFile>> action) {

        final MediaLoader mediaLoader=new MediaLoader();
        Album.initialize(AlbumConfig.newBuilder(activity)
                .setAlbumLoader(mediaLoader)
                .setLocale(Locale.getDefault())
                .build());


        Album.image(activity)
                .singleChoice()
                .camera(true)
                .columnCount(2)
                .widget(
                        Widget.newDarkBuilder(activity)
                                .title(activity.getString(R.string.select_restaurant_image))
                                .build()
                )


                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                       AlbumFile albumFile=result.get(0);
                       mediaLoader.load(imageView,albumFile.getPath());

                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Toast.makeText(activity, activity.getString(R.string.canceled), Toast.LENGTH_LONG).show();
                    }
                })
                .start();


    }
}
