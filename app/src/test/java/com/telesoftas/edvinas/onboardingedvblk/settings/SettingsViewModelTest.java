package com.telesoftas.edvinas.onboardingedvblk.settings;

import com.telesoftas.edvinas.onboardingedvblk.utils.network.listeners.SourcesRequestListener;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.SourcesResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class SettingsViewModelTest {

    public static final String DEFAULT_SOURCE = "defaultSource";
    public static final int DEFAULT_SOURCE_POSITION = 0;
    public static final boolean CHECK_BOX_UNCHECKED = false;
    public static final boolean CHECKBOX_CHECKED = true;
    private SettingsModel model;
    private SettingsView view;
    private SettingsViewModel presenter;
    private ArgumentCaptor<SourcesRequestListener> captor;
    private SourcesResponse response;
    private Throwable throwable;

    @Before
    public void setUp() throws Exception {
        view = mock(SettingsView.class);
        model = mock(SettingsModel.class);
        presenter = new SettingsViewModel(model);
        captor = ArgumentCaptor.forClass(SourcesRequestListener.class);
        response = mock(SourcesResponse.class);
        presenter.takeView(view);
        throwable = mock(IOException.class);
    }

    @Test
    public void onCreate_hasView_callsModelGetSources() {
        presenter.onCreate();

        verify(model).getSources(captor.capture());
    }

    @Test
    public void onCreate_hasView_setsViewNotificationCheckBox() {
        when(model.isCheckBoxChecked()).thenReturn(CHECKBOX_CHECKED);

        presenter.onCreate();

        verify(view).setNotificationCheckBoxEnabled(CHECKBOX_CHECKED);
    }

    @Test
    public void onCreate_onSourcesReceived_callsViewPopulateView() {
        when(model.getCurrentSelectedId()).thenReturn(DEFAULT_SOURCE_POSITION);
        presenter.onCreate();
        verify(model).getSources(captor.capture());

        captor.getValue().onSourcesReceived(response);

        verify(view).populateDropDown(response.getSources(), DEFAULT_SOURCE_POSITION);
    }

    @Test
    public void onCreate_onSourcesReceived_callsViewSetOnItemSelectedListener() {
        presenter.onCreate();
        verify(model).getSources(captor.capture());

        captor.getValue().onSourcesReceived(response);

        verify(view).setOnItemSelectedListener();
    }

    @Test
    public void onCreate_onSourcesReceived_callsViewHideProgress() {
        presenter.onCreate();
        verify(model).getSources(captor.capture());

        captor.getValue().onSourcesReceived(response);

        verify(view).hideProgress();
    }

    @Test
    public void onCreate_onFailureThrowableNotIOException_doesNotCallViewMethods() {
        Throwable throwable = mock(Exception.class);
        presenter.onCreate();
        verify(model).getSources(captor.capture());

        captor.getValue().onFailure(throwable);

        verify(view).setNotificationCheckBoxEnabled(anyBoolean());
        verifyNoMoreInteractions(view);
    }

    @Test
    public void onCreate_onFailureHasNoView_doesNotCallViewMethods() {
        presenter.onCreate();
        verify(model).getSources(captor.capture());
        presenter.dropView();

        captor.getValue().onFailure(throwable);

        verify(view).setNotificationCheckBoxEnabled(anyBoolean());
        verifyNoMoreInteractions(view);
    }

    @Test
    public void onCreate_onFailureThrowableIOException_callsViewShowEmptyDropDown() {
        presenter.onCreate();
        verify(model).getSources(captor.capture());

        captor.getValue().onFailure(throwable);

        verify(view).showEmptyDropDown();
    }

    @Test
    public void onCreate_onFailureThrowableIOException_callsViewShowSettingsScreen() {
        presenter.onCreate();
        verify(model).getSources(captor.capture());

        captor.getValue().onFailure(throwable);

        verify(view).showSettingsScreen();
    }

    @Test
    public void onCreate_onFailureThrowableIOException_callsViewHideProgress() {
        presenter.onCreate();
        verify(model).getSources(captor.capture());

        captor.getValue().onFailure(throwable);

        verify(view).hideProgress();
    }

    @Test
    public void onItemSelected_hasView_callsModelSaveSourceId() {
        presenter.onItemSelected(DEFAULT_SOURCE, DEFAULT_SOURCE_POSITION);

        verify(model).saveSourceId(DEFAULT_SOURCE);
    }

    @Test
    public void onItemSelected_callsModelSaveSourcePosition() {
        presenter.onItemSelected(DEFAULT_SOURCE, DEFAULT_SOURCE_POSITION);

        verify(model).saveSourcePosition(DEFAULT_SOURCE_POSITION);
    }

    @Test
    public void onShowTutorialClick_hasView_callsViewShowTutorialScreen() {
        presenter.onShowTutorialClick();

        verify(view).showTutorialScreen();
    }

    @Test
    public void onShowTutorialClick_hasView_doesNotCallViewMethods() {
        presenter.dropView();

        presenter.onShowTutorialClick();

        verifyZeroInteractions(view);
    }

    @Test
    public void onClickCheckBox_hasNoView_callsModelSaveCheckBoxStateDoesNotCallView() {
        presenter.dropView();

        presenter.onClickCheckBox(CHECK_BOX_UNCHECKED);

        verify(model).saveCheckBoxState(CHECK_BOX_UNCHECKED);
        verifyZeroInteractions(view);
    }

    @Test
    public void onClickCheckBox_checked_callsModelSubscribeAndViewShowSelectedPopUp() {
        presenter.onClickCheckBox(CHECKBOX_CHECKED);

        verify(model).subscribeNotifications();
        verify(view).showCheckBoxSelectedPopUp();
    }

    @Test
    public void onClickCheckBox_unchecked_callsModelUnsubscribeAndViewShowUnselectedPopUp() {
        presenter.onClickCheckBox(CHECK_BOX_UNCHECKED);

        verify(model).unsubscribeNotifications();
        verify(view).showCheckBoxUnselectedPopUp();
    }
}