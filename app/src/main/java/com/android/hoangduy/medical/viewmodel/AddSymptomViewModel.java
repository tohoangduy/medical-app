package com.android.hoangduy.medical.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.hoangduy.medical.model.entity.Symptom;
import com.android.hoangduy.medical.model.entity.SymptomTracking;
import com.android.hoangduy.medical.persistence.MedicalRepository;

import java.util.List;

public class AddSymptomViewModel extends ViewModel {

    private MedicalRepository repository;

    public AddSymptomViewModel(MedicalRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Symptom>> getSymptoms() {
        return repository.getListSymptoms();
    }

    public void addSymptom(Symptom symptom) {
        new AddSymptomAsyncTask().execute(symptom);
    }

    public void addSymptomTracking(SymptomTracking symptomTracking) {
        new AddSymptomTrackingAsyncTask().execute(symptomTracking);
    }

    private class AddSymptomAsyncTask extends AsyncTask<Symptom, Void, Long> {
        @Override
        protected Long doInBackground(Symptom... symptoms) {
            return repository.insertSymptom(symptoms[0]);
        }
    }

    private class AddSymptomTrackingAsyncTask extends AsyncTask<SymptomTracking, Void, Long> {
        @Override
        protected Long doInBackground(SymptomTracking... symptomTrackings) {
            return repository.insertSymptomTracking(symptomTrackings[0]);
        }
    }
}
