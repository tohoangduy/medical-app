package com.android.hoangduy.medical.adapter;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.listeners.ICallback;
import com.android.hoangduy.medical.model.dto.MedicationDTO;
import com.android.hoangduy.medical.views.SpinnerButtons;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationVH> {

    private List<MedicationDTO> mDataset;
    private Activity context;
    private ICallback callback;

    public MedicationAdapter(Activity context, List<MedicationDTO> dataset) {
        this.context = context;
        this.mDataset = dataset;
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
        holder.setMedicationImage(mDataset.get(position).image);
        holder.setQuanity(mDataset.get(position).quantity);
        holder.setTimesPerDay(mDataset.get(position).takeTimeList.size());
        holder.setTakeMedication(mDataset.get(position).isBeforeMeal);
        holder.setTimeList(mDataset.get(position).takeTimeList);
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public void setCallBack(ICallback cb) {
        callback = cb;
    }

    protected class MedicationVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View mView;
        private List<String> datasetTime;
        private RecyclerView rcTime;
        private TextInputEditText edMedicationName;

        public MedicationVH(View view) {
            super(view);
            mView = view;

            mView.findViewById(R.id.btnSave).setOnClickListener(this);
            mView.findViewById(R.id.btnAddTime).setOnClickListener(this);
            mView.findViewById(R.id.imgMedication).setOnClickListener(this);
            mView.findViewById(R.id.minimizeContainer).setOnClickListener(this);
        }

        public void setMediactionName(String name) {
            edMedicationName = mView.findViewById(R.id.edMedicationName);
            edMedicationName.setText(name);
        }

        public void setMedicationImage(byte[] image) {
            AppCompatImageView imageView = mView.findViewById(R.id.imgMedication);
            Glide.with(context)
                    .load(image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }

        public void setQuanity(int quanity) {
            SpinnerButtons spinerFrequence = mView.findViewById(R.id.spinerFrequence);
            spinerFrequence.setValue(quanity);
        }

        public void setTimesPerDay(int times) {
            SpinnerButtons spinerDose = mView.findViewById(R.id.spinerDose);
            spinerDose.setValue(times);
        }

        public void setTakeMedication(boolean isBeforeMeal) {
            MaterialButtonToggleGroup toggleGroup = mView.findViewById(R.id.toggleGroup);
            toggleGroup.check(isBeforeMeal ? R.id.btnBefore : R.id.btnAfter);
        }

        public void setTimeList(List<String> timeList) {
            datasetTime = timeList;
            rcTime = mView.findViewById(R.id.rcTime);
            rcTime.setAdapter(new StringAdapter(context, datasetTime));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSave:
                    edMedicationName.setFocusable(false);
                    mView.findViewById(R.id.detailPanel).setVisibility(View.GONE);
                    callback.onSaved();
                    break;
                case R.id.btnAddTime:
                    datasetTime.add("_ _:_ _");
                    rcTime.getAdapter().notifyItemInserted(datasetTime.size());
                    break;
                case R.id.imgMedication:
//                    Intent captureIntent = new Intent(context, CaptureActivity.class);
//                    context.startActivityForResult(captureIntent, CameraUtils.CAMERA_REQUEST);

                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    context.startActivityForResult(camera, 1221);
                break;
                case R.id.minimizeContainer:
                    break;
            }
        }
    }
}
