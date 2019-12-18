package com.android.hoangduy.medical.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.base.BaseFragment;

public class MainFrgmt extends BaseFragment implements View.OnClickListener {
    public static MainFrgmt newInstance() {
        return new MainFrgmt();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView != null) return contentView;
        contentView = inflater.inflate(R.layout.frgmt_main, container, false);

        // get references
        View btnAddMedication = contentView.findViewById(R.id.btnAddMedication);
        View btnAddSymptoms = contentView.findViewById(R.id.btnAddSymptoms);
        View btnSummary = contentView.findViewById(R.id.btnSummary);

        // set listeners
        btnAddMedication.setOnClickListener(this);
        btnAddSymptoms.setOnClickListener(this);
        btnSummary.setOnClickListener(this);

        return contentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddMedication:
                goNext(AddMedicationFrgmt.newInstance());
                break;

            case R.id.btnAddSymptoms:
                goNext(AddSymptomsFrgmt.newInstance());
                break;

            case R.id.btnSummary:
                goNext(SummaryFrgmt.newInstance());
                break;
        }
    }
}
