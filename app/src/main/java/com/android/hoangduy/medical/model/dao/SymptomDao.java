package com.android.hoangduy.medical.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.hoangduy.medical.model.entity.Symptom;

import java.util.List;

@Dao
public interface SymptomDao {

    @Query("SELECT * FROM Symptom")
    LiveData<List<Symptom>> getListSymptoms();

    @Insert
    void insertSymptom(Symptom symptom);

    @Query("SELECT * FROM Symptom WHERE id = :id")
    LiveData<Symptom> getSymptomById(String id);

    @Delete
    void deleteSymptom(Symptom symptom);
}
