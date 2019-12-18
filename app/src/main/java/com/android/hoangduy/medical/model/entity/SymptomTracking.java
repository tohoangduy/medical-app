package com.android.hoangduy.medical.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class SymptomTracking {
    @PrimaryKey
    @NonNull
    private long id;
    @ForeignKey(entity = Symptom.class, parentColumns = "id", childColumns = "symptomId")
    private long symptomId;
    private String level;
    private long atDate;

    public SymptomTracking(long id, long symptomId, String level, long atDate) {
        this.id = id;
        this.symptomId = symptomId;
        this.level = level;
        this.atDate = atDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(long symptomId) {
        this.symptomId = symptomId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getAtDate() {
        return atDate;
    }

    public void setAtDate(long atDate) {
        this.atDate = atDate;
    }
}
