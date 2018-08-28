package com.telesoftas.edvinas.onboardingedvblk.settings;

import com.telesoftas.edvinas.onboardingedvblk.utils.firebase.NotificationSubscriber;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.SourcesService;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.listeners.SourcesRequestListener;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.SourcesResponse;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants.KEY_NOTIFICATIONS_STATE;
import static com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants.KEY_SOURCE_ID;
import static com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants.KEY_SOURCE_POSITION;
import static com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants.SUBSCRIBE_TOPIC;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SettingsModelImplTest {
    private static final int DEFAULT_ITEM_POSITION = 0;
    private static final String DEFAULT_SOURCE_ID = "defaultSourceId";
    private static final int DEFAULT_SOURCE_POSITION = 0;
    private static final boolean DEFAULT_NOTIFICATION_STATE = false;
    private StorageManager storageManager;
    private SourcesService service;
    private SettingsModelImpl model;
    private Call<SourcesResponse> call;
    private SourcesResponse response;
    private ArgumentCaptor<Callback<SourcesResponse>> captor;
    private SourcesRequestListener listener;
    private NotificationSubscriber subscriber;

    @Before
    public void setUp() throws Exception {
        service = mock(SourcesService.class);
        storageManager = mock(StorageManager.class);
        subscriber = mock(NotificationSubscriber.class);
        model = new SettingsModelImpl(service, storageManager, subscriber);
        listener = mock(SourcesRequestListener.class);
        call = mock(Call.class);
        captor = ArgumentCaptor.forClass(Callback.class);
        when(service.getSources()).thenReturn(call);
        doNothing().when(call).enqueue(captor.capture());
    }

    @Test
    public void getSources_callsSourcesServiceGetSources() {
        model.getSources(listener);

        verify(service).getSources();
    }

    @Test
    public void getSources_onResponse_callsListenerOnSourcesReceived() {
        model.getSources(listener);

        captor.getValue().onResponse(call, Response.success(response));

        verify(listener).onSourcesReceived(response);
    }

    @Test
    public void getSources_onFailure_callsListenerOnFailure() {
        Throwable throwable = new Throwable();
        model.getSources(listener);

        captor.getValue().onFailure(call, throwable);

        verify(listener).onFailure(throwable);
    }

    @Test
    public void saveSourceId_callsStoragemanagerPutString() {
        model.saveSourceId(DEFAULT_SOURCE_ID);

        verify(storageManager).putString(KEY_SOURCE_ID, DEFAULT_SOURCE_ID);
    }

    @Test
    public void saveSourceId_callsStoragemanagerPutInt() {
        model.saveSourcePosition(DEFAULT_SOURCE_POSITION);

        verify(storageManager).putInt(KEY_SOURCE_POSITION, DEFAULT_SOURCE_POSITION);
    }

    @Test
    public void getCurrentSelectedId_callsStorageManagerGetInt() {
        model.getCurrentSelectedId();

        verify(storageManager).getInt(KEY_SOURCE_POSITION, DEFAULT_ITEM_POSITION);
    }

    @Test
    public void isCheckBoxChecked_callsStorageManagerGetBoolean() {
        model.isCheckBoxChecked();

        verify(storageManager).getBoolean(KEY_NOTIFICATIONS_STATE, DEFAULT_NOTIFICATION_STATE);
    }

    @Test
    public void saveCheckBoxState_callsStorageManagerPutBoolean() {
        model.saveCheckBoxState(DEFAULT_NOTIFICATION_STATE);

        verify(storageManager).putBoolean(KEY_NOTIFICATIONS_STATE, DEFAULT_NOTIFICATION_STATE);
    }

    @Test
    public void subscribeNotifications_callsSubscriberSubscribeNotifications() {
        model.subscribeNotifications();

        verify(subscriber).subscribeNotifications(SUBSCRIBE_TOPIC);
    }

    @Test
    public void unsubscribeNotifications_callsSubscriberUnsubscribeNotifications() {
        model.unsubscribeNotifications();

        verify(subscriber).unsubscribeNotifications(SUBSCRIBE_TOPIC);
    }
}