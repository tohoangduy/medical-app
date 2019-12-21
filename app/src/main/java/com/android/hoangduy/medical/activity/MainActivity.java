package com.android.hoangduy.medical.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.base.BaseActivity;
import com.android.hoangduy.medical.fragment.MainFrgmt;
import com.android.hoangduy.medical.ui.UIHelper;
import com.android.hoangduy.medical.utils.Const;
import com.android.hoangduy.medical.utils.SharePref;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends BaseActivity {
    @Override
    protected Fragment getFirstFragment() {
        return MainFrgmt.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_layout;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isSkipIntro = SharePref.getBoolean(getApplicationContext(), Const.KEY_SKIP_INTRO, false);
        if (!isSkipIntro) {
            Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            startActivity(intent);
        }
    }
}
