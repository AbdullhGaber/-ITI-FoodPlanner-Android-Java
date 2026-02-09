package com.example.foodplannerapp.presentation.home.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal_area.Area;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder> {

    private final DiffUtil.ItemCallback<Area> diffCallback = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Area oldItem, @NonNull Area newItem) {
            return oldItem.getAreaName().equals(newItem.getAreaName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Area oldItem, @NonNull Area newItem) {
            return oldItem.getAreaName().equals(newItem.getAreaName());
        }
    };

    private final AsyncListDiffer<Area> differ = new AsyncListDiffer<>(this, diffCallback);

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area, parent, false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        Area area = differ.getCurrentList().get(position);
        holder.bind(area);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<Area> list) {
        differ.submitList(list);
    }

    public static class AreaViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgFlag;
        private final TextView tvAreaName;
        private final Context context;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imgFlag = itemView.findViewById(R.id.imgFlag);
            tvAreaName = itemView.findViewById(R.id.tvAreaName);
        }

        public void bind(Area area) {
            tvAreaName.setText(area.getAreaName());

            Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                    .setDuration(1800)
                    .setBaseAlpha(0.7f)
                    .setHighlightAlpha(0.6f)
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true)
                    .build();

            ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
            shimmerDrawable.setShimmer(shimmer);

            Glide.with(context)
                    .load(area.getFlagUrl())
                    .placeholder(shimmerDrawable)
                    .error(R.drawable.ic_broken_image)
                    .into(imgFlag);
        }
    }
}