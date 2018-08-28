package com.telesoftas.edvinas.onboardingedvblk.utils.presenter;

public abstract class BasePresenterImpl<T> implements BasePresenter<T> {
    private T view;

    @Override
    public void takeView(T view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null; //NOPMD
    }

    public final T getView() {
        return view;
    }

    public final boolean hasView() {
        return view != null;
    }

    protected final void onView(Action<T> action) {
        if (hasView()) {
            action.invoke(view);
        }
    }
}
