package com.learn.locationmanagement.ui.common.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {
    private Context context;
    private static final RequestOptions requestOptions;
    static {
        requestOptions = (new RequestOptions()).centerCrop();
    }

    public ImageLoader(Context context) {
        this.context = context;
    }

    public void loadImage(@NonNull String imageUrl, @DrawableRes int placeHolderRes, @DrawableRes int errorHolderRes, @NonNull ImageView target) {
        RequestBuilder<Drawable> loader = Glide.with(context)
                .load(imageUrl);
        if (placeHolderRes != -1) {
            loader = loader.placeholder(placeHolderRes);
        }
        if (errorHolderRes != -1) {
            loader = loader.error(errorHolderRes);
        }
        loader.into(target);
    }
}
