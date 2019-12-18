package com.android.hoangduy.medical.base;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    protected View contentView;

    protected void goBack() {
        hideSoftKeyboard();
        if (getActivity() != null)
            getActivity().onBackPressed();
    }

    protected void goNext(Fragment next) {
        hideSoftKeyboard();
        ((BaseActivity) getActivity()).goNext(next);
    }

    protected void hideSoftKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            imm = null;
        }

    }

    protected void hideSoftKeyboard() {
        try {
            hideSoftKeyboard(contentView);
        } catch (Exception e) {

        }
        clearFocusView();
    }

    protected void clearFocusView() {
        try {
            View view = getActivity().getWindow().getCurrentFocus();
            if (view != null)
                view.clearFocus();
        } catch (Exception e) {

        }
    }

    protected boolean onBackPressed() {
        return false;
    }
}
