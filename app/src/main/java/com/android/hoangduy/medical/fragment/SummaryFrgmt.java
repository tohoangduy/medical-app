package com.android.hoangduy.medical.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.base.BaseFragment;

public class SummaryFrgmt extends BaseFragment {
    public static SummaryFrgmt newInstance() {
        return new SummaryFrgmt();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView != null) return contentView;
        contentView = inflater.inflate(R.layout.frgmt_summary, container, false);

        return contentView;
    }
}
