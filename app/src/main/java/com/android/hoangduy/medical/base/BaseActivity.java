package com.android.hoangduy.medical.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.hoangduy.medical.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract Fragment getFirstFragment();

    protected abstract int getContentViewId();

    protected abstract int getFragmentContainerId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        Fragment fragment = getFirstFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainerId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void goNext(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                )
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void popTo(String name) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack(name, 0);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fr = getCurrentFragment();
        if (fr instanceof BaseFragment && ((BaseFragment) fr).onBackPressed()) {
            // already handle in fragment
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        }
    }

    public boolean isFirstFragment() {
        try {
            return getSupportFragmentManager().getBackStackEntryCount() == 1;
        } catch (Exception ignored) {

        }
        return false;
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(getFragmentContainerId());
    }
}
