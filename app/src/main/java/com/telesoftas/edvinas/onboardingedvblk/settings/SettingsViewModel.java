package com.telesoftas.edvinas.onboardingedvblk.settings;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;

import com.telesoftas.edvinas.onboardingedvblk.utils.firebase.NotificationSubscriber;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.SourcesService;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.SourcesResponse;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import timber.log.Timber;

public class SettingsViewModel extends ViewModel {
    private static final int DEFAULT_ITEM_POSITION = 0;
    private static final boolean DEFAULT_NOTIFICATION_STATE = false;
    private static final String DEFAULT_EMPTY_SOURCE = "";
    private final SourcesService service;
    private final StorageManager storageManager;
    private final NotificationSubscriber subscriber;
    private final Scheduler scheduler;
    private Disposable disposable = Disposables.disposed();

    private MutableLiveData<Pair<List<Source>, Integer>> sourcesState = new MutableLiveData<>();
    private MutableLiveData<Boolean> emptyState = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressState = new MutableLiveData<>();
    private MutableLiveData<Boolean> notificationState = new MutableLiveData<>();

    public LiveData<Pair<List<Source>, Integer>> observeSourcesState() {
        return sourcesState;
    }

    public LiveData<Boolean> observeProgressState() {
        return progressState;
    }

    public LiveData<Boolean> observeEmptyState() {
        return emptyState;
    }

    public LiveData<Boolean> observeNotificationState() {
        return notificationState;
    }

    public SettingsViewModel(
            SourcesService service,
            StorageManager storageManager,
            NotificationSubscriber subscriber,
            Scheduler scheduler) {
        this.service = service;
        this.storageManager = storageManager;
        this.subscriber = subscriber;
        this.scheduler = scheduler;
    }

    public void onLoad() {
        notificationState.postValue(isCheckBoxChecked());
        disposable = service.getSources()
                .observeOn(scheduler)
                .doOnSubscribe(disposable1 -> progressState.postValue(true))
                .subscribe(this::onSourcesReceived, this::onSourcesRequestError);
    }

    private void onSourcesReceived(SourcesResponse sourcesResponse) {
        sourcesState.postValue(Pair.create(sourcesResponse.getSources(), getCurrentSelectedId()));
        progressState.postValue(false);
    }

    private void onSourcesRequestError(Throwable throwable) {
        Timber.e(throwable);
        progressState.postValue(false);
        emptyState.postValue(true);
    }

    public void onSpinnerItemClicked(String sourceId, int position) {
        if (!sourceId.equals(getCurrentSource())) {
            saveSourceId(sourceId);

        }
        saveSourcePosition(position);
    }

    public void onClickCheckbox(boolean isChecked) {
        storageManager.putBoolean(PreferencesConstants.KEY_NOTIFICATIONS_STATE, isChecked);
        if (isChecked) {
            subscriber.subscribeNotifications(PreferencesConstants.SUBSCRIBE_TOPIC);
        } else {
            subscriber.unsubscribeNotifications(PreferencesConstants.SUBSCRIBE_TOPIC);
        }
    }

    private boolean isCheckBoxChecked() {
        return storageManager.getBoolean(PreferencesConstants.KEY_NOTIFICATIONS_STATE,
                DEFAULT_NOTIFICATION_STATE);
    }

    private int getCurrentSelectedId() {
        return storageManager.getInt(PreferencesConstants.KEY_SOURCE_POSITION,
                DEFAULT_ITEM_POSITION);
    }

    private String getCurrentSource() {
        return storageManager.getString(PreferencesConstants.KEY_SOURCE_ID, DEFAULT_EMPTY_SOURCE);
    }

    private void saveSourceId(String source) {
        storageManager.putString(PreferencesConstants.KEY_SOURCE_ID, source);
    }

    private void saveSourcePosition(int position) {
        storageManager.putInt(PreferencesConstants.KEY_SOURCE_POSITION, position);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}

