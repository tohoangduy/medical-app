package com.android.hoangduy.medical.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.hoangduy.medical.R;

import java.util.Calendar;
import java.util.List;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.VH> {

    private List<String> dataset;
    private Context context;

    public StringAdapter(Context context, List<String> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public StringAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_layout, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StringAdapter.VH holder, int position) {
        holder.setTime(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        private Button timeView;

        public VH(@NonNull View itemView) {
            super(itemView);
            timeView = (Button) itemView;
            timeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTimePicker();
                }
            });
        }

        private void openTimePicker() {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            timeView.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        public void setTime(String time) {
            timeView.setText(time);
        }
    }
}
