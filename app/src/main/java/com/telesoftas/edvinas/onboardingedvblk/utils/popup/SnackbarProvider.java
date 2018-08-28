package com.telesoftas.edvinas.onboardingedvblk.utils.popup;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.telesoftas.edvinas.onboardingedvblk.R;

public class SnackbarProvider {
    public static final int PADDING_SIZE = 6;
    private final int errorColor;
    private final View view;

    public SnackbarProvider(View view) {
        this.view = view;
        errorColor = ContextCompat.getColor(view.getContext(), R.color.errorRed);
    }

    public void showErrorSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(errorColor);
        snackbar.getView().setPadding(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE);
        snackbar.show();
    }

    public void showSnackbar(String message){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setPadding(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE);
        snackbar.show();
    }
}
