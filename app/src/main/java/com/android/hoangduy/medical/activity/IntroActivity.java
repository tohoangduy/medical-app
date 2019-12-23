package com.android.hoangduy.medical.activity;

import androidx.fragment.app.Fragment;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.base.BaseActivity;
import com.android.hoangduy.medical.fragment.IntroFrgmt;

public class IntroActivity extends BaseActivity {
    @Override
    protected Fragment getFirstFragment() {
        return IntroFrgmt.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.empty_layout;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }
}
