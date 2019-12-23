package com.android.hoangduy.medical.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Medication {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String name;
    private byte[] image;
    private int quantity;
    private boolean isBeforeMeal;
    private boolean status;
    private String takeTime;
    private String date;

    public Medication(String name, byte[] image, int quantity, boolean status,
                      boolean isBeforeMeal, String takeTime, String date) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.status = status;
        this.isBeforeMeal = isBeforeMeal;
        this.takeTime = takeTime;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isBeforeMeal() {
        return isBeforeMeal;
    }

    public void setBeforeMeal(boolean beforeMeal) {
        isBeforeMeal = beforeMeal;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
