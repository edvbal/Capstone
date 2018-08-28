package com.telesoftas.edvinas.onboardingedvblk.tutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class TutorialActivity extends AppCompatActivity implements TutorialView {
    private Unbinder unbinder;
    @BindView(R.id.tutorialViewPager) ViewPager viewPager;
    @BindView(R.id.tutorialButton) Button buttonTutorial;
    @BindView(R.id.tutorialPageIndicator) CirclePageIndicator tutorialPageIndicator;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private TutorialPresenter presenter;

    public static Intent createIntent(Context context) {
        return new Intent(context, TutorialActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        unbinder = ButterKnife.bind(this);
        initPresenter();
        setUpViewPager();
    }

    @OnClick(R.id.tutorialButton)
    void onTutorialButtonClick() {
        presenter.onTutorialButtonClick();
    }

    private void initPresenter() {
        presenter = new TutorialPresenterFactory(this).create();
        presenter.takeView(this);
    }

    private void setUpViewPager() {
        TutorialPagerAdapter pagerAdapter = new TutorialPagerAdapter(
                getSupportFragmentManager(),
                getResources().obtainTypedArray(R.array.tutorial_images),
                getResources().obtainTypedArray(R.array.tutorial_descriptions)
        );
        viewPager.setAdapter(pagerAdapter);
        tutorialPageIndicator.setViewPager(viewPager);
        initOnPageChangeListener(pagerAdapter);
    }

    private void initOnPageChangeListener(final TutorialPagerAdapter pagerAdapter) {
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(
                    int position, float positionOffset, int positionOffsetPixels
            ) {
                // empty
            }

            @Override
            public void onPageSelected(int position) {
                presenter.onPageSelected(position, pagerAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // empty
            }
        };
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void setIndicatorInvisible() {
        tutorialPageIndicator.setVisibility(INVISIBLE);
    }

    @Override
    public void setIndicatorVisible() {
        tutorialPageIndicator.setVisibility(VISIBLE);
    }

    @Override
    public void setButtonVisible() {
        buttonTutorial.setVisibility(VISIBLE);
    }

    @Override
    public void setButtonInvisible() {
        buttonTutorial.setVisibility(INVISIBLE);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    private void removeOnPageChangeListener() {
        viewPager.removeOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void onDestroy() {
        removeOnPageChangeListener();
        presenter.dropView();
        unbinder.unbind();
        super.onDestroy();
    }
}
