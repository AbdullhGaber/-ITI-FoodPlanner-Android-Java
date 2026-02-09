package com.example.foodplannerapp.presentation.meals.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal_ingeredient.Ingredient;
import com.example.foodplannerapp.databinding.ItemIngredientBinding;
import com.example.foodplannerapp.presentation.utils.ShimmerUtil;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private final DiffUtil.ItemCallback<Ingredient> diffCallback = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return oldItem.getIdIngredient().equals(newItem.getIdIngredient());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return oldItem.getStrIngredient().equals(newItem.getStrIngredient());
        }
    };

    private final AsyncListDiffer<Ingredient> differ = new AsyncListDiffer<>(this, diffCallback);

    public void submitList(List<Ingredient> list) {
        differ.submitList(list);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemIngredientBinding binding = ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final ItemIngredientBinding binding;

        public IngredientViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Ingredient ingredient) {
            binding.tvIngredientName.setText(ingredient.getStrIngredient());
            ShimmerUtil.addShimmerToImage(itemView.getContext(), ingredient.getStrThumb(), binding.ivIngredient);
        }
    }
}