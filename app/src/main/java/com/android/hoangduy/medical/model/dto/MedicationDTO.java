package com.android.hoangduy.medical.model.dto;

import java.util.ArrayList;
import java.util.List;

public class MedicationDTO {
    public String date;
    public String name;
    public byte[] image;
    public int quantity;
    public boolean isBeforeMeal;
    public List<String> takeTimeList = new ArrayList<>();
}
