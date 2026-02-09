package com.example.foodplannerapp.presentation.home.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplannerapp.data.model.meal_area.Area;
import com.example.foodplannerapp.databinding.ItemAreaBinding;
import com.example.foodplannerapp.databinding.ItemAreaGridBinding;
import com.example.foodplannerapp.presentation.utils.ShimmerUtil;

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
    private final boolean isGrid;

    public AreaAdapter() {
        this.isGrid = false;
    }

    public AreaAdapter(boolean isGrid) {
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        
        if (isGrid) {
            ItemAreaGridBinding binding = ItemAreaGridBinding.inflate(inflater, parent, false);
            return new AreaViewHolder(binding.getRoot(), binding.imgFlag, binding.tvAreaName, parent.getContext());
        } else {
            ItemAreaBinding binding = ItemAreaBinding.inflate(inflater, parent, false);
            return new AreaViewHolder(binding.getRoot(), binding.imgFlag, binding.tvAreaName, parent.getContext());
        }
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

        public AreaViewHolder(@NonNull android.view.View itemView, ImageView imgFlag, TextView tvAreaName, Context context) {
            super(itemView);
            this.imgFlag = imgFlag;
            this.tvAreaName = tvAreaName;
            this.context = context;
        }

        public void bind(Area area) {
            tvAreaName.setText(area.getAreaName());
            ShimmerUtil.addShimmerToImage(context,area.getFlagUrl(), imgFlag);
        }
    }
}