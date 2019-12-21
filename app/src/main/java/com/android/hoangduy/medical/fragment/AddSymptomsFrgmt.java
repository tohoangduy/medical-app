package com.android.hoangduy.medical.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.android.hoangduy.medical.MedicationApplication;
import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.base.BaseFragment;
import com.android.hoangduy.medical.model.entity.Symptom;
import com.android.hoangduy.medical.model.entity.SymptomTracking;
import com.android.hoangduy.medical.ui.UIHelper;
import com.android.hoangduy.medical.utils.DateUtil;
import com.android.hoangduy.medical.viewmodel.AddSymptomViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;

public class AddSymptomsFrgmt extends BaseFragment implements View.OnClickListener {

    private LinearLayout symptomsContainer;
    private ImageButton btnAddSymptoms;
    private TextInputEditText edNewSymptom;
    private AddSymptomViewModel viewModel;

    public static AddSymptomsFrgmt newInstance() {
        return new AddSymptomsFrgmt();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set up and subscribe (observe) to the ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AddSymptomViewModel.class);

        loadSymptoms();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView != null) return contentView;
        contentView = inflater.inflate(R.layout.frgmt_add_symptom, container, false);
        setTitle("Add Symptoms");

        ((MedicationApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        // get references
        symptomsContainer = contentView.findViewById(R.id.symptomsContainer);
        btnAddSymptoms = contentView.findViewById(R.id.btnAddSymptoms);
        edNewSymptom = contentView.findViewById(R.id.edNewSymptom);
        TextView tvDate = contentView.findViewById(R.id.tvDate);

        // add listeners
        btnAddSymptoms.setOnClickListener(this);
        edNewSymptom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doAddSymptom(v.getText().toString());
                    edNewSymptom.setVisibility(View.INVISIBLE);
                }

                return false; // return false to hide keyboard
            }
        });

        // init data default on views
        String dateFormatted = DateUtil.date2String(new Date(), "dd MMMM yyyy");
        tvDate.setText(dateFormatted);

        return contentView;
    }

    private void doAddSymptom(String symptomName) {
        Symptom symptom = new Symptom(symptomName);
        viewModel.addSymptom(symptom);
        doAddSymptomToView(symptom);
    }

    private void loadSymptoms() {
        viewModel.getSymptoms().observe(this, (List<Symptom> symptoms) -> {
            if (symptoms != null) {
                showSymptoms(symptoms);
            }
        });
    }

    private void showSymptoms(List<Symptom> symptoms) {
        symptomsContainer.removeAllViews();
        for (Symptom symptom : symptoms) {
            doAddSymptomToView(symptom);
        }
    }

    private void doAddSymptomToView(Symptom symptom) {
        View symptomLine = getLayoutInflater().inflate(R.layout.symptom_line, symptomsContainer, false);

        TextView tvSymptomName = symptomLine.findViewById(R.id.tvSymptomName);
        tvSymptomName.setText(symptom.getName());
        RadioGroup radioGroup = symptomLine.findViewById(R.id.rdGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String level = "";
                switch (checkedId) {
                    case R.id.rdMild:
                        Log.d(getTag(), symptom.getName() + ", checkedId = rdMild");
                        level = getString(R.string.mild);
                        break;
                    case R.id.rdModerate:
                        Log.d(getTag(), symptom.getName() + ", checkedId = rdModerate");
                        level = getString(R.string.moderate);
                        break;
                    case R.id.rdSevere:
                        Log.d(getTag(), symptom.getName() + ", checkedId = rdSevere");
                        level = getString(R.string.severe);
                        break;
                }

                viewModel.addSymptomTracking(
                        new SymptomTracking(
                                symptom.getId(),
                                level,
                                DateUtil.getCurrentDate().getTime()
                        )
                );
            }
        });

        symptomsContainer.addView(symptomLine);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddSymptoms) {
            edNewSymptom.setText("");
            edNewSymptom.setVisibility(View.VISIBLE);
            UIHelper.showSoftKeyBoard(getContext(), edNewSymptom);
        }
    }
}
