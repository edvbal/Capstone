package com.telesoftas.edvinas.onboardingedvblk.tutorial;

import com.telesoftas.edvinas.onboardingedvblk.utils.presenter.BasePresenterImpl;

class TutorialPresenterImpl extends BasePresenterImpl<TutorialView> implements TutorialPresenter {
    private final TutorialModel model;

    public TutorialPresenterImpl(TutorialModel model) {
        super();
        this.model = model;
    }

    @Override
    public void onPageSelected(int pagePosition, int pagesCount) {
        onView(view -> {
            if (isLastItem(pagePosition, pagesCount)) {
                view.setIndicatorInvisible();
                view.setButtonVisible();
            } else {
                view.setIndicatorVisible();
                view.setButtonInvisible();
            }
        });
    }

    @Override
    public void onTutorialButtonClick() {
        onView(view -> {
            view.finishActivity();
            model.setTutorialSeen();
        });
    }

    private boolean isLastItem(int position, int pagesCount) {
        return position == pagesCount - 1;
    }
}
