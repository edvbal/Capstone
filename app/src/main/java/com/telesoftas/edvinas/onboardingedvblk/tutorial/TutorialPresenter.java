package com.telesoftas.edvinas.onboardingedvblk.tutorial;

import com.telesoftas.edvinas.onboardingedvblk.utils.presenter.BasePresenter;

interface TutorialPresenter extends BasePresenter<TutorialView> {
    void onPageSelected(int position, int count);

    void onTutorialButtonClick();
}
