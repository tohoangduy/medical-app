package com.android.hoangduy.medical.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.base.BaseFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainFrgmt extends BaseFragment implements View.OnClickListener {
    public static MainFrgmt newInstance() {
        return new MainFrgmt();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowToolBar(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setShowToolBar(false);
    }

    @Override
    public void onPause() {
        setShowToolBar(true);
        super.onPause();
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

    @Override
    protected boolean onBackPressed() {
        new MaterialAlertDialogBuilder(getContext())
                .setMessage(getString(R.string.exit_app_msg))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> getActivity().finish())
                .setNegativeButton(getString(R.string.no), null)
                .show();

        return true;
    }
}
