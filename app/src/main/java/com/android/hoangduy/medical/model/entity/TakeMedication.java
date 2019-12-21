package com.android.hoangduy.medical.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class TakeMedication {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    @ForeignKey(entity = Medication.class, parentColumns = "id", childColumns = "id")
    private long medicationId;
    private long takeTime;
    private boolean status;

    public TakeMedication(long id, long medicationId, long takeTime) {
        this.id = id;
        this.medicationId = medicationId;
        this.takeTime = takeTime;
        this.status = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(long medicationId) {
        this.medicationId = medicationId;
    }

    public long getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(long takeTime) {
        this.takeTime = takeTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
