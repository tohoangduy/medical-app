package com.android.hoangduy.medical.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.hoangduy.medical.model.entity.Medication;

import java.util.List;

@Dao
public interface MedicationDao {

    @Query("SELECT * FROM Medication")
    LiveData<List<Medication>> getListMedications();

    @Insert
    void insertMedication(Medication symptom);

    @Query("SELECT * FROM Medication where id = :id")
    LiveData<Medication> getMedicationById(long id);

    @Delete
    void deleteMedication(Medication medication);
}
