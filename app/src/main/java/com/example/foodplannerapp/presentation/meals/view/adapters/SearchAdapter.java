package com.example.foodplannerapp.presentation.meals.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.databinding.ItemMealSearchBinding; // Create this XML
import com.example.foodplannerapp.presentation.utils.ShimmerUtil;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private final OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(Meal meal);
    }

    public SearchAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    private final DiffUtil.ItemCallback<Meal> diffCallback = new DiffUtil.ItemCallback<>() {
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
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealSearchBinding binding = ItemMealSearchBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private final ItemMealSearchBinding binding;

        public SearchViewHolder(ItemMealSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Meal meal) {
            binding.tvMealName.setText(meal.getStrMeal());
            if(meal.getStrArea() != null) binding.tvMealArea.setText(meal.getStrArea());

            ShimmerUtil.addShimmerToImage(itemView.getContext(), meal.getStrMealThumb(), binding.ivMealImg);

            binding.getRoot().setOnClickListener(v -> listener.onMealClick(meal));
        }
    }
}