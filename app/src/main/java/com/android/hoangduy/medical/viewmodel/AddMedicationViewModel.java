package com.android.hoangduy.medical.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.hoangduy.medical.model.dto.MedicationDTO;
import com.android.hoangduy.medical.model.entity.Medication;
import com.android.hoangduy.medical.persistence.MedicalRepository;

import java.util.List;

public class AddMedicationViewModel extends ViewModel {

    private MedicalRepository repository;

    public AddMedicationViewModel(MedicalRepository repository) {
        this.repository = repository;
    }

    public void addMedication(MedicationDTO medicationDTO) {
        String taketimeList = "";
        for (String value : medicationDTO.takeTimeList) {
            taketimeList = taketimeList.concat(value) + ";";
        }
        taketimeList = taketimeList.substring(0, taketimeList.lastIndexOf(";"));

        Medication medication = new Medication(
                medicationDTO.name,
                medicationDTO.image,
                medicationDTO.quantity,
                false,
                medicationDTO.isBeforeMeal,
                taketimeList,
                medicationDTO.date
        );
        new AddMedicationAsyncTask().execute(medication);
    }

    public LiveData<List<Medication>> loadMedications(String dateString) {
        return repository.getListMedications(dateString);
    }

    public LiveData<List<Medication>> loadMedications() {
        return repository.getListMedications();
    }

    private class AddMedicationAsyncTask extends AsyncTask<Medication, Void, Long> {
        @Override
        protected Long doInBackground(Medication... medications) {
            return repository.insertMedication(medications[0]);
        }
    }
}
