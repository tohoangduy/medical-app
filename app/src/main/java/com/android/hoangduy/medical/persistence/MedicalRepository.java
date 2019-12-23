package com.android.hoangduy.medical.persistence;

import androidx.lifecycle.LiveData;

import com.android.hoangduy.medical.model.dao.MedicationDao;
import com.android.hoangduy.medical.model.dao.SymptomDao;
import com.android.hoangduy.medical.model.dao.SymptomTrackingDao;
import com.android.hoangduy.medical.model.entity.Medication;
import com.android.hoangduy.medical.model.entity.Symptom;
import com.android.hoangduy.medical.model.entity.SymptomTracking;

import java.util.List;

import javax.inject.Inject;

public class MedicalRepository {
    private final MedicationDao medicationDao;
    private final SymptomDao symptomDao;
    private final SymptomTrackingDao symptomTrackingDao;

    @Inject
    public MedicalRepository(
            MedicationDao medicationDao,
            SymptomDao symptomDao,
            SymptomTrackingDao symptomTrackingDao
    ) {
        this.medicationDao = medicationDao;
        this.symptomDao = symptomDao;
        this.symptomTrackingDao = symptomTrackingDao;
    }

    public LiveData<List<Medication>> getListMedications(String date) {
        return medicationDao.getListMedications(date);
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

    /* SYMPTOM */

    public LiveData<List<Symptom>> getListSymptoms() {
        return symptomDao.getListSymptoms();
    }

    public LiveData<Symptom> getSymptomById(Long itemId) {
        return symptomDao.getSymptomById(itemId);
    }

    public Long insertSymptom(Symptom symptom) {
        return symptomDao.insertSymptom(symptom);
    }

    public void deleteSymptom(Symptom symptom) {
        symptomDao.deleteSymptom(symptom);
    }

    /* SYMPTOM_TRACKING */
    public LiveData<List<SymptomTracking>> getListSymptomTrackings() {
        return symptomTrackingDao.getListSymptomTrackings();
    }

    public LiveData<SymptomTracking> getSymptomTrackingById(Long itemId) {
        return symptomTrackingDao.getSymptomTrackingById(itemId);
    }

    public Long insertSymptomTracking(SymptomTracking symptomTracking) {
        return symptomTrackingDao.insertSymptomTracking(symptomTracking);
    }

    public void deleteSymptomTracking(SymptomTracking symptomTracking) {
        symptomTrackingDao.deleteSymptomTracking(symptomTracking);
    }

    public Long insertMedication(Medication medication) {
        return medicationDao.insertMedication(medication);
    }
}
