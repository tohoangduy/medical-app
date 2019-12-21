package com.android.hoangduy.medical.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.hoangduy.medical.model.entity.SymptomTracking;

import java.util.List;

@Dao
public interface SymptomTrackingDao {
    @Query("SELECT * FROM SymptomTracking")
    LiveData<List<SymptomTracking>> getListSymptomTrackings();

    @Insert
    Long insertSymptomTracking(SymptomTracking symptomTracking);

    @Query("SELECT * FROM SymptomTracking WHERE id = :id")
    LiveData<SymptomTracking> getSymptomTrackingById(Long id);

    @Delete
    void deleteSymptomTracking(SymptomTracking symptomTracking);
}
