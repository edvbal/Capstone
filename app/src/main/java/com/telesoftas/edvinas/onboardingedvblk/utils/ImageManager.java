package com.telesoftas.edvinas.onboardingedvblk.utils;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.telesoftas.edvinas.onboardingedvblk.R;

public class ImageManager {
    public void loadImageFromUrl(ImageView imageView, String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            loadErrorImage(imageView);
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .placeholder(R.drawable.news_place_holder)
                    .error(R.drawable.news_error_loading_image)
                    .into(imageView);
        }
    }

    private void loadErrorImage(ImageView imageView) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(),
                R.drawable.news_error_loading_image));
    }
}
