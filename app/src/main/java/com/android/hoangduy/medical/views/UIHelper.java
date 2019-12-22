package com.android.hoangduy.medical.views;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class UIHelper {
    /**
     * This function help to focus and show soft keyboard on the edittext
     * @param context
     * @param editText - edittext focused to show keyboard
     */
    public static void showSoftKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
}
