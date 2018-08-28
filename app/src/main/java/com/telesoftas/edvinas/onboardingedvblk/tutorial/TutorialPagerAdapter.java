package com.telesoftas.edvinas.onboardingedvblk.tutorial;

import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.telesoftas.edvinas.onboardingedvblk.tutorial.details.TutorialDetailsDetailsFragment;

public class TutorialPagerAdapter extends FragmentPagerAdapter {
    private static final int DEFAULT_RESOURCE_VALUE = 0;
    private final TypedArray imageResources;
    private final TypedArray descriptionResources;

    public TutorialPagerAdapter(
            FragmentManager fragmentManager,
            TypedArray imageResources, TypedArray descriptionResources
    ) {
        super(fragmentManager);
        this.imageResources = imageResources;
        this.descriptionResources = descriptionResources;
    }

    @Override
    public Fragment getItem(int position) {
        return TutorialDetailsDetailsFragment.newInstance(
                imageResources.getResourceId(position, DEFAULT_RESOURCE_VALUE),
                descriptionResources.getResourceId(position, DEFAULT_RESOURCE_VALUE)
        );
    }

    @Override
    public int getCount() {
        return imageResources.length();
    }
}
