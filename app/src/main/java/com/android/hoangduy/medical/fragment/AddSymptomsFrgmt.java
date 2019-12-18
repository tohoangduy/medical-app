package com.android.hoangduy.medical.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.base.BaseFragment;

public class AddSymptomsFrgmt extends BaseFragment {
    public static AddSymptomsFrgmt newInstance() {
        return new AddSymptomsFrgmt();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView != null) return contentView;
        contentView = inflater.inflate(R.layout.frgmt_add_symptom, container, false);

        return contentView;
    }
}
