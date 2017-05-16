package com.youcii.mvplearn.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.youcii.mvplearn.R;

/**
 * Created by YouCii on 2016/7/15.
 */
public class ToastUtils {

    public static void showShortSnack(View rootView, String content) {
        Snackbar snackbar = Snackbar.make(rootView, content, Snackbar.LENGTH_SHORT);

        Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView = (TextView) ve.findViewById(R.id.snackbar_text);
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

    public static void showShortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
