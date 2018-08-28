package com.telesoftas.edvinas.onboardingedvblk.landing;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class SplashViewModelTest {
    private SplashModel model;
    private SplashPresenter presenter;
    private SplashView view;

    @Before
    public void setUp() throws Exception {
        model = mock(SplashModel.class);
        view = mock(SplashView.class);
        presenter = new SplashViewModel(model);
        presenter.takeView(view);
    }

    @Test
    public void onCreate_isTutorialSeen_callsViewStartMainScreen() {
        when(model.isTutorialSeen()).thenReturn(true);

        presenter.onCreate();

        verify(view).startMainScreen();
    }

    @Test
    public void onCreate_isTutorialNotSeen_callsViewStartTutorialScreen() {
        when(model.isTutorialSeen()).thenReturn(false);

        presenter.onCreate();

        verify(view).startTutorialScreen();
    }

    @Test
    public void onCreate_callsViewFinishScreen() {
        presenter.onCreate();

        verify(view).finishScreen();
    }

    @Test
    public void onCreate_hasNoView_doesNotCallViewAndModel() {
        presenter.dropView();

        presenter.onCreate();

        verifyZeroInteractions(model);
        verifyZeroInteractions(view);
    }
}