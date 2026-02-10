package com.example.foodplannerapp.presentation.favorites.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.data.db.meals.entities.Meal;
import com.example.foodplannerapp.databinding.ItemMealFavBinding;


import java.util.List;

public class FavoriteMealsAdapter extends RecyclerView.Adapter<FavoriteMealsAdapter.FavViewHolder> {
    public interface OnFavoriteClickListener {
        void onMealClick(Meal meal);
        void onDeleteClick(Meal meal);
    }

    private final OnFavoriteClickListener listener;

    public FavoriteMealsAdapter(OnFavoriteClickListener listener) {
        this.listener = listener;
    }

    private final DiffUtil.ItemCallback<Meal> diffCallback = new DiffUtil.ItemCallback<Meal>() {
        @Override
        public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
            return oldItem.getIdMeal().equals(newItem.getIdMeal());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
            return oldItem.getIdMeal().equals(newItem.getIdMeal()) && 
                   oldItem.getStrMeal().equals(newItem.getStrMeal());
        }
    };

    private final AsyncListDiffer<Meal> differ = new AsyncListDiffer<>(this, diffCallback);

    public void submitList(List<Meal> list) {
        differ.submitList(list);
    }


    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealFavBinding binding = ItemMealFavBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new FavViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        private final ItemMealFavBinding binding;

        public FavViewHolder(ItemMealFavBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Meal meal) {
            binding.tvFavMealName.setText(meal.getStrMeal());
            binding.tvFavMealArea.setText(String.format("%s • %s", meal.getStrArea(), meal.getStrCategory()));

            // Load image from local bytes if available, otherwise from remote URL
            if (meal.getLocalImageBytes() != null && meal.getLocalImageBytes().length > 0) {
                Glide.with(binding.getRoot().getContext())
                        .load(meal.getLocalImageBytes())
                        .into(binding.ivFavMeal);
            } else {
                Glide.with(binding.getRoot().getContext())
                        .load(meal.getStrMealThumb())
                        .into(binding.ivFavMeal);
            }

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onMealClick(meal);
            });

            binding.btnDeleteFav.setOnClickListener(v -> {
                if (listener != null) listener.onDeleteClick(meal);
            });
        }
    }
}