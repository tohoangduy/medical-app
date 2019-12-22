package com.android.hoangduy.medical.activity;

import androidx.fragment.app.Fragment;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.base.BaseActivity;
import com.android.hoangduy.medical.utils.CameraUtils;

public class CaptureActivity extends BaseActivity {

    public static final int REQUEST_CODE = 1010;
    private static final String TAG = CaptureActivity.class.getSimpleName();

    @Override
    protected Fragment getFirstFragment() {
        return new CameraUtils();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_layout;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    public CaptureActivity() {
    }
}
