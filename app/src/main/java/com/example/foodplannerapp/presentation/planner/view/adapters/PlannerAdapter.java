package com.example.foodplannerapp.presentation.planner.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.db.meals.entities.PlanMeal;
import com.example.foodplannerapp.databinding.ItemMealFavBinding;
import com.example.foodplannerapp.presentation.utils.ShimmerUtil;

import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.PlanViewHolder> {

    public interface OnPlanClickListener {
        void onPlanClick(PlanMeal plan);
        void onDeletePlanClick(PlanMeal plan);
    }

    private final OnPlanClickListener listener;

    public PlannerAdapter(OnPlanClickListener listener) {
        this.listener = listener;
    }

    private final DiffUtil.ItemCallback<PlanMeal> diffCallback = new DiffUtil.ItemCallback<PlanMeal>() {
        @Override
        public boolean areItemsTheSame(@NonNull PlanMeal oldItem, @NonNull PlanMeal newItem) {
            return oldItem.getId() == newItem.getId(); // Compare DB IDs
        }

        @Override
        public boolean areContentsTheSame(@NonNull PlanMeal oldItem, @NonNull PlanMeal newItem) {
            return oldItem.getMealId().equals(newItem.getMealId());
        }
    };

    private final AsyncListDiffer<PlanMeal> differ = new AsyncListDiffer<>(this, diffCallback);

    public void submitList(List<PlanMeal> list) {
        differ.submitList(list);
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Reuse the item_meal_fav layout
        ItemMealFavBinding binding = ItemMealFavBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new PlanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        private final ItemMealFavBinding binding;

        public PlanViewHolder(ItemMealFavBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PlanMeal plan) {
            binding.tvFavMealName.setText(plan.getStrMeal());
            binding.tvFavMealArea.setText(String.format("%s • %s", plan.getStrArea(), plan.getStrCategory()));

            if (plan.getImageBytes() != null && plan.getImageBytes().length > 0) {
                Glide.with(binding.getRoot()).load(plan.getImageBytes()).into(binding.ivFavMeal);
            } else {
                ShimmerUtil.addShimmerToImage(itemView.getContext(), plan.getStrMealThumb(), binding.ivFavMeal);
            }

            binding.getRoot().setOnClickListener(v -> listener.onPlanClick(plan));
            binding.btnDeleteFav.setOnClickListener(v -> listener.onDeletePlanClick(plan));
        }
    }
}