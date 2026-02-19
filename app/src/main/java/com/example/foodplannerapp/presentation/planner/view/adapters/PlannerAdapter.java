package com.example.foodplannerapp.presentation.planner.view.adapters;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.data.db.meals.entities.MealEntity;
import com.example.foodplannerapp.databinding.ItemMealFavBinding;

import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.PlannerViewHolder> {
    private final DiffUtil.ItemCallback<MealEntity> diffCallback = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull MealEntity oldItem, @NonNull MealEntity newItem) {
            return oldItem.getIdMeal().equals(newItem.getIdMeal()) &&
                    oldItem.getDayOfWeek().equals(newItem.getDayOfWeek());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MealEntity oldItem, @NonNull MealEntity newItem) {
            return oldItem.getStrMeal().equals(newItem.getStrMeal());
        }
    };
    private final AsyncListDiffer<MealEntity> differ = new AsyncListDiffer<>(this, diffCallback);

    private final OnPlanClickListener listener;

    public interface OnPlanClickListener {
        void onPlanClick(MealEntity plan);
        void onDeletePlanClick(MealEntity plan);
    }

    public PlannerAdapter(OnPlanClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<MealEntity> list) {
        differ.submitList(list);
    }

    @NonNull
    @Override
    public PlannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealFavBinding binding = ItemMealFavBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PlannerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlannerViewHolder holder, int position) {
        MealEntity plan = differ.getCurrentList().get(position);
        holder.bind(plan);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public class PlannerViewHolder extends RecyclerView.ViewHolder {
        private final ItemMealFavBinding binding;

        public PlannerViewHolder(ItemMealFavBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MealEntity plan) {
            binding.tvFavMealName.setText(plan.getStrMeal());

            if (plan.getLocalImageBytes() != null && plan.getLocalImageBytes().length > 0) {
                Glide.with(binding.getRoot().getContext())
                        .load(plan.getLocalImageBytes())
                        .into(binding.ivFavMeal);
            } else {
                Glide.with(binding.getRoot().getContext())
                        .load(plan.getStrMealThumb())
                        .into(binding.ivFavMeal);
            }

            binding.getRoot().setOnClickListener(v -> listener.onPlanClick(plan));
            binding.btnDeleteFav.setOnClickListener(v -> listener.onDeletePlanClick(plan));
        }
    }
}