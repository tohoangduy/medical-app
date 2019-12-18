package com.android.hoangduy.medical.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.hoangduy.medical.model.dao.MedicationDao;
import com.android.hoangduy.medical.model.dao.SymptomDao;
import com.android.hoangduy.medical.model.entity.Medication;
import com.android.hoangduy.medical.model.entity.Symptom;

@Database(entities = {Medication.class, Symptom.class}, version = 1, exportSchema = false)
public abstract class MedicalDB extends RoomDatabase {
    public abstract MedicationDao medicationDao();
    public abstract SymptomDao symptomDao();
}
