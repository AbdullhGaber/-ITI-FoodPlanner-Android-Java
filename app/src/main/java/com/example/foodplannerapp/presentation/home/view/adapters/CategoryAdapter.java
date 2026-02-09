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
import com.example.foodplannerapp.data.model.meal_category.Category;
import com.example.foodplannerapp.databinding.ItemCategoryBinding;
import com.example.foodplannerapp.databinding.ItemCategoryGridBinding;
import com.example.foodplannerapp.presentation.utils.ShimmerUtil;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final DiffUtil.ItemCallback<Category> diffCallback = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };

    private final AsyncListDiffer<Category> differ = new AsyncListDiffer<>(this, diffCallback);

    private final boolean isGrid;

    public CategoryAdapter() {
        this.isGrid = false;
    }

    public CategoryAdapter(boolean isGrid) {
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        
        if (isGrid) {
            ItemCategoryGridBinding binding = ItemCategoryGridBinding.inflate(inflater, parent, false);
            return new CategoryViewHolder(binding.getRoot(), binding.imgCategory, binding.tvCategoryName, parent.getContext());
        } else {
            ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater, parent, false);
            return new CategoryViewHolder(binding.getRoot(), binding.imgCategory, binding.tvCategoryName, parent.getContext());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<Category> list) {
        differ.submitList(list);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView tvCategoryName;
        Context context;

        public CategoryViewHolder(@NonNull android.view.View itemView, ImageView imgCategory, TextView tvCategoryName, Context context) {
            super(itemView);
            this.imgCategory = imgCategory;
            this.tvCategoryName = tvCategoryName;
            this.context = context;
        }

        public void bind(Category category) {
            tvCategoryName.setText(category.getName());
            ShimmerUtil.addShimmerToImage(context, category.getImage(), imgCategory);
        }
    }
}