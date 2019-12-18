package com.android.hoangduy.medical.persistence;

import androidx.lifecycle.LiveData;

import com.android.hoangduy.medical.model.dao.MedicationDao;
import com.android.hoangduy.medical.model.dao.SymptomDao;
import com.android.hoangduy.medical.model.entity.Medication;
import com.android.hoangduy.medical.model.entity.Symptom;

import java.util.List;

import javax.inject.Inject;

public class MedicalRepository {
    private final MedicationDao medicationDao;
    private final SymptomDao symptomDao;

    @Inject
    public MedicalRepository(MedicationDao medicationDao, SymptomDao symptomDao) {
        this.medicationDao = medicationDao;
        this.symptomDao = symptomDao;
    }


    public LiveData<List<Medication>> getListMedications() {
        return medicationDao.getListMedications();
    }

    public LiveData<Medication> getMedicationById(long itemId) {
        return medicationDao.getMedicationById(itemId);
    }

    public void createNewMedication(Medication medication) {
        medicationDao.insertMedication(medication);
    }

    public void deleteMedication(Medication medication) {
        medicationDao.deleteMedication(medication);
    }

    public LiveData<List<Symptom>> getListSymptoms() {
        return symptomDao.getListSymptoms();
    }

    public LiveData<Symptom> getSymptom(String itemId) {
        return symptomDao.getSymptomById(itemId);
    }

    public void createNewSymptom(Symptom symptom) {
        symptomDao.insertSymptom(symptom);
    }

    public void deleteSymptom(Symptom symptom) {
        symptomDao.deleteSymptom(symptom);
    }
}
