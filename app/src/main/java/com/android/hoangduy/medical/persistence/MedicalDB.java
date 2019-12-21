package com.android.hoangduy.medical.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.hoangduy.medical.model.dao.MedicationDao;
import com.android.hoangduy.medical.model.dao.SymptomDao;
import com.android.hoangduy.medical.model.dao.SymptomTrackingDao;
import com.android.hoangduy.medical.model.entity.Medication;
import com.android.hoangduy.medical.model.entity.Symptom;
import com.android.hoangduy.medical.model.entity.SymptomTracking;

@Database(
        entities = {
                Medication.class,
                Symptom.class,
                SymptomTracking.class
        },
        version = 2,
        exportSchema = false
)
public abstract class MedicalDB extends RoomDatabase {
    public abstract MedicationDao getMedicationDao();
    public abstract SymptomDao getSymptomDao();
    public abstract SymptomTrackingDao getSymptomTrackingDao();
}
