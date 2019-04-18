package com.youcii.mvplearn.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.app.App;

/**
 * @author YouCii
 * @date 2016/7/15
 */
@SuppressLint("ShowToast")
public class ToastUtils {

    public static void showShortSnack(View rootView, String content) {
        Snackbar snackbar = Snackbar.make(rootView, content, Snackbar.LENGTH_SHORT);

        Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView = ve.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#0BB668"));

        snackbar.show();
    }

    public static void showLongSnack(View rootView, String content) {
        Snackbar snackbar = Snackbar.make(rootView, content, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showButtonSnack(View rootView, String content, String buttonName, View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar.make(rootView, content, Snackbar.LENGTH_LONG);
        snackbar.setAction(buttonName, clickListener);
        snackbar.show();
    }

    private static Toast toast;

    public static void showShortToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(text);
        }
        toast.show();
    }

    public static void showLongToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(App.getContext(), text, Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(text);
        }
        toast.show();
    }
}
