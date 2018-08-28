package com.telesoftas.edvinas.onboardingedvblk.utils.presenter;

public interface BasePresenter<T> {
    void takeView(T view);

    void dropView();
}
