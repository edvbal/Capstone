package com.telesoftas.edvinas.onboardingedvblk.tutorial;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class TutorialPresenterImplTest {
    public static final int LAST_PAGE_POSITION = 4;
    public static final int NOT_LAST_PAGE_POSITION = 2;
    public static final int PAGES_COUNT = 5;
    private TutorialView view;
    private TutorialPresenterImpl presenter;
    private TutorialModel model;

    @Before
    public void setUp() throws Exception {
        view = mock(TutorialView.class);
        model = mock(TutorialModel.class);
        presenter = new TutorialPresenterImpl(model);
        presenter.takeView(view);
    }

    @Test
    public void onPageSelected_lastPage_callsViewSetIndicatorInvisible() {
        presenter.onPageSelected(LAST_PAGE_POSITION, PAGES_COUNT);

        verify(view).setIndicatorInvisible();
    }

    @Test
    public void onPageSelected_lastPage_callsViewSetButtonVisible() {
        presenter.onPageSelected(LAST_PAGE_POSITION, PAGES_COUNT);

        verify(view).setButtonVisible();
    }

    @Test
    public void onPageSelected_notLastPage_callsViewSetIndicatorVisible() {
        presenter.onPageSelected(NOT_LAST_PAGE_POSITION, PAGES_COUNT);

        verify(view).setIndicatorVisible();
    }

    @Test
    public void onPageSelected_notLastPage_callsViewSetButtonInvisible() {
        presenter.onPageSelected(NOT_LAST_PAGE_POSITION, PAGES_COUNT);

        verify(view).setButtonInvisible();
    }

    @Test
    public void onPageSelected_hasNoView_doesNotCallViewMethods() {
        presenter.dropView();
        presenter.onPageSelected(LAST_PAGE_POSITION, PAGES_COUNT);

        verifyZeroInteractions(view);
    }

    @Test
    public void onTutorialButtonClick_hasNoView_doesNotCallViewOrModelMethods() {
        presenter.dropView();
        presenter.onTutorialButtonClick();

        verifyZeroInteractions(view);
        verifyZeroInteractions(model);
    }

    @Test
    public void onTutorialButtonClick_hasView_callsViewFinishActivityAndModelSetTutorialSeen() {
        presenter.onTutorialButtonClick();

        verify(view).finishActivity();
        verify(model).setTutorialSeen();
    }
}