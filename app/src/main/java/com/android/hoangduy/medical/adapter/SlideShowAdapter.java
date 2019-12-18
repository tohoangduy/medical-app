package com.android.hoangduy.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.hoangduy.medical.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class SlideShowAdapter<T> extends RecyclerView.Adapter<SlideShowAdapter<T>.ItemViewHolder> {

    private List<T> dataset;
    private Context context;

    public SlideShowAdapter(Context context, List<T> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgSlide);
        }

        public ImageView getImage() {
            return image;
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.slide_item, parent, false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context)
                .load(dataset.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImage());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
