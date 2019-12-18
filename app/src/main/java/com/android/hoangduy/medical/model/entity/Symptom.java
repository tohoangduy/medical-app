package com.android.hoangduy.medical.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Symptom {

    @PrimaryKey
    @NonNull
    private long id;
    private String name;

    public Symptom(@NonNull long id, String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
