package com.telesoftas.edvinas.onboardingedvblk.utils.presenter;

public interface Action<T> {
    void invoke(T view);
}
