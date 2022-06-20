package com.learn.locationmanagement.ui.common.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.learn.locationmanagement.di.fragment.FragmentScope;

import javax.inject.Inject;

public class ImageLoader {
    private Context context;
    private static final RequestOptions requestOptions;
    static {
        requestOptions = (new RequestOptions()).centerCrop();
    }

    public ImageLoader(Context context) {
        this.context = context;
    }

    private Fragment fragment;

    public ImageLoader (Fragment fragment) {
        this.fragment = fragment;
    }

    public void loadImageWithFragment(@NonNull String imageUrl, @DrawableRes int placeHolderRes, @DrawableRes int errorHolderRes, @NonNull ImageView target) {
        RequestBuilder<Drawable> loader = Glide.with(fragment)
                .load(imageUrl);
        if (placeHolderRes != -1) {
            loader = loader.placeholder(placeHolderRes);
        }
        if (errorHolderRes != -1) {
            loader = loader.error(errorHolderRes);
        }
        loader.into(target);
    }
    public void loadImageWithContext(@NonNull String imageUrl, @DrawableRes int placeHolderRes, @DrawableRes int errorHolderRes, @NonNull ImageView target) {
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
