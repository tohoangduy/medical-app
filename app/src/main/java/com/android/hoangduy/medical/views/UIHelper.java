package com.android.hoangduy.medical.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.hoangduy.medical.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

    public static void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new MaterialAlertDialogBuilder(context)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    public static void showMessageOK(Context context, String title, String message, DialogInterface.OnClickListener okListener) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .show();
    }

    private static ProgressDialog loadingDialog;
    public static void showLoadingDialog(Context context) {
        if (loadingDialog == null)
            loadingDialog = ProgressDialog.show(context, "", "Loading ...", true);
        else
            loadingDialog.show();
    }

    public static void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
