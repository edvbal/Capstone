package com.telesoftas.edvinas.onboardingedvblk.tutorial;

import com.telesoftas.edvinas.onboardingedvblk.tutorial.details.TutorialDetailsPresenterImpl;
import com.telesoftas.edvinas.onboardingedvblk.tutorial.details.TutorialDetailsView;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class TutorialDetailsPresenterImplTest {
    private TutorialDetailsView view;
    private TutorialDetailsPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(TutorialDetailsView.class);
        presenter = new TutorialDetailsPresenterImpl();
    }

    @Test
    public void onViewCreated_hasView_callsViewSetViews() {
        presenter.takeView(view);
        presenter.onViewCreated();

        verify(view).setViews();
    }

    @Test
    public void onViewCreated_hasNoView_doesntCallView() {
        presenter.dropView();
        presenter.onViewCreated();

        verifyZeroInteractions(view);
    }
}