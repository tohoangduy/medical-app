package com.android.hoangduy.medical.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity
public class Medication {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    @ForeignKey(entity = Medicament.class, parentColumns = "id", childColumns = "id")
    private long meidcamentId;
    private int quantity;

    public Medication(long id, long meidcamentId, int quantity) {
        this.id = id;
        this.meidcamentId = meidcamentId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMeidcamentId() {
        return meidcamentId;
    }

    public void setMeidcamentId(long meidcamentId) {
        this.meidcamentId = meidcamentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
