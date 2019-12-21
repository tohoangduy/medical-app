package com.android.hoangduy.medical.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected View contentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(true); // default fragment will show back button on the Action Bar
        setRetainInstance(true);
    }

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

    protected void setTitle(String title) {
        ((BaseActivity) getActivity()).setTitle(title);
    }

    protected void setDisplayHomeAsUpEnabled(boolean isDisplay) {
        ((BaseActivity) getActivity()).setDisplayHomeAsUpEnabled(isDisplay);
    }

    protected void setShowToolBar(boolean isShow) {
        ((BaseActivity) getActivity()).setShowToolBar(isShow);
    }
}
