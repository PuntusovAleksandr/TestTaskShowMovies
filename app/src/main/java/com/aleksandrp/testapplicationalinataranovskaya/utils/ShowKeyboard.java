package com.aleksandrp.testapplicationalinataranovskaya.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class ShowKeyboard {
    public static void showKeynoard(final Activity mActivity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                InputMethodManager imm =
                        (InputMethodManager) mActivity.getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }, 500);
    }

    public static void hideKeynoard(final Activity mActivity, View mView) {
        InputMethodManager imm =
                (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
    }

}
