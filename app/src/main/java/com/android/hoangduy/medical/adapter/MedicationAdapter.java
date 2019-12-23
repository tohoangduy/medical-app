package com.android.hoangduy.medical.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.listeners.ICallback;
import com.android.hoangduy.medical.model.dto.MedicationDTO;
import com.android.hoangduy.medical.utils.FileUtil;
import com.android.hoangduy.medical.views.SpinnerButtons;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationVH> {

    private List<MedicationDTO> mDataset;
    private Activity context;
    private Fragment fragment;
    private ICallback callback;
    private int takePictureIndex = 0;

    public MedicationAdapter(Activity context, List<MedicationDTO> dataset, Fragment fragment) {
        this.context = context;
        this.mDataset = dataset;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MedicationAdapter.MedicationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_detail, parent, false);

        return new MedicationVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationAdapter.MedicationVH holder, int position) {
        holder.setMediactionName(mDataset.get(position).name);
        if (mDataset.get(position).image != null) {
            holder.setMedicationImage(mDataset.get(position).image);
        }
        holder.setQuanity(mDataset.get(position).quantity);
        holder.setTimesPerDay(mDataset.get(position).takeTimeList.size());
        holder.setTakeMedication(mDataset.get(position).isBeforeMeal);
        holder.setTimeList(mDataset.get(position).takeTimeList);
        holder.setIndex(position);
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public void setCallBack(ICallback cb) {
        callback = cb;
    }

    public void onCropImageResult(Uri uriImage) {
        InputStream iStream = null;
        try {
            iStream = context.getContentResolver().openInputStream(uriImage);
            mDataset.get(takePictureIndex).image = FileUtil.getBytes(iStream);
            notifyItemChanged(takePictureIndex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class MedicationVH extends RecyclerView.ViewHolder implements View.OnClickListener, SpinnerButtons.OnValueChange, MaterialButtonToggleGroup.OnButtonCheckedListener {

        private List<String> datasetTime;
        private RecyclerView rcTime;
        private TextInputEditText edMedicationName;
        private AppCompatImageView imgMedication;
        private View detailPanel;
        private int index;
        private SpinnerButtons spinerFrequence, spinerDose;
        private MaterialButtonToggleGroup toggleGroup;

        MedicationVH(View view) {
            super(view);

            imgMedication = view.findViewById(R.id.imgMedication);
            detailPanel = view.findViewById(R.id.detailPanel);
            edMedicationName = view.findViewById(R.id.edMedicationName);
            imgMedication = view.findViewById(R.id.imgMedication);
            spinerFrequence = view.findViewById(R.id.spinerFrequence);
            spinerDose = view.findViewById(R.id.spinerDose);
            toggleGroup = view.findViewById(R.id.toggleGroup);
            rcTime = view.findViewById(R.id.rcTime);

            view.findViewById(R.id.btnSave).setOnClickListener(this);
            view.findViewById(R.id.btnAddTime).setOnClickListener(this);
            imgMedication.setOnClickListener(this);
            edMedicationName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mDataset.get(index).name = s.toString();
                }
            });
            spinerFrequence.setOnValueChangeListener(this);
            spinerDose.setOnValueChangeListener(this);
            toggleGroup.addOnButtonCheckedListener(this);

            detailPanel.setVisibility(View.GONE);
            edMedicationName.setEnabled(false);
        }

        void setMediactionName(String name) {
            edMedicationName.setText(name);
        }

        void setMedicationImage(byte[] image) {
            Glide.with(context)
                    .load(image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgMedication);
        }

        void setQuanity(int quanity) {
            spinerFrequence.setValue(quanity);
        }

        void setTimesPerDay(int times) {
            spinerDose.setValue(times);
        }

        void setTakeMedication(boolean isBeforeMeal) {
            toggleGroup.check(isBeforeMeal ? R.id.btnBefore : R.id.btnAfter);
        }

        void setTimeList(List<String> timeList) {
            datasetTime = timeList;
            rcTime.setAdapter(new StringAdapter(context, datasetTime));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSave:
                    edMedicationName.setEnabled(false);
                    detailPanel.setVisibility(View.GONE);
                    callback.onSaved(index);
                    break;
                case R.id.btnAddTime:
                    spinerDose.setValue(spinerDose.getValue() + 1);
                    addTakeTime();
                    break;
                case R.id.imgMedication:
                    takePictureIndex = this.index;
                    if (detailPanel.getVisibility() == View.VISIBLE) {
                        Uri uri = new Intent().getData();
                        CropImage.activity(uri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(context, fragment);
                    } else {
                        detailPanel.setVisibility(View.VISIBLE);
                        edMedicationName.setEnabled(true);
                    }
                    break;

                case R.id.edMedicationName:
                    break;
            }
        }

        private void addTakeTime() {
            datasetTime.add("_ _:_ _");
            rcTime.getAdapter().notifyItemInserted(datasetTime.size());
        }

        void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public void onChange(int id, int value) {
            switch (id) {
                case R.id.spinerFrequence:
                    mDataset.get(index).quantity = value;
                    break;

                case R.id.spinerDose:
                    if (mDataset.get(index).takeTimeList.size() < value)
                        addTakeTime();
                    else
                        removeTakeTime();
                    break;
            }
        }

        private void removeTakeTime() {
            int lastIndex = datasetTime.size() - 1;
            datasetTime.remove(lastIndex);
            rcTime.getAdapter().notifyItemRemoved(lastIndex);
        }

        @Override
        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
            mDataset.get(index).isBeforeMeal = (checkedId == 0 && isChecked);
        }
    }
}
