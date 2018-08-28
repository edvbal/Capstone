package com.telesoftas.edvinas.onboardingedvblk.tutorial.details;

import com.telesoftas.edvinas.onboardingedvblk.utils.presenter.BasePresenterImpl;

public class TutorialDetailsPresenterImpl extends BasePresenterImpl<TutorialDetailsView>
        implements TutorialDetailsPresenter {
    @Override
    public void onViewCreated() {
        onView(TutorialDetailsView::setViews);
    }
}
